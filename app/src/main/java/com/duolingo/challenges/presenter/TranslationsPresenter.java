package com.duolingo.challenges.presenter;

import android.os.Bundle;
import android.view.MotionEvent;

import com.duolingo.challenges.contract.TranslationsContract;
import com.duolingo.challenges.data.Solution;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.data.models.WordCoordinate;
import com.duolingo.challenges.usecases.CoordinatesArrayUseCase;
import com.duolingo.challenges.usecases.CoordinatesComparatorUseCase;
import com.duolingo.challenges.usecases.FlagSelectorUseCase;
import com.duolingo.challenges.usecases.PositionCoordinateUseCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TranslationsPresenter extends ReactivePresenter implements TranslationsContract.Presenter {
    private static final int INVALID_FIRST_CHARACTER = -1;
    private static final String KEY_SOLUTIONS = "KEY_SOLUTIONS";
    private final Translation translation;
    private final PositionCoordinateUseCase positionCoordinate;
    private final CoordinatesArrayUseCase coordinatesArray;
    private final CoordinatesComparatorUseCase coordinatesComparator;
    private final FlagSelectorUseCase flagsSelector;
    private TranslationsContract.View view;
    private WordCoordinate pivotCoordinate;
    private int pivotCharacterPosition = INVALID_FIRST_CHARACTER;
    private final int gridSize;
    private final List<Integer> lastPositionsSelection = new ArrayList<>();
    private ArrayList<Solution> solutions = new ArrayList<>();

    public TranslationsPresenter(Translation translation,
                                 PositionCoordinateUseCase positionCoordinate,
                                 CoordinatesArrayUseCase coordinatesArray,
                                 CoordinatesComparatorUseCase coordinatesComparator,
                                 FlagSelectorUseCase flagsSelector) {
        this.translation = translation;
        this.positionCoordinate = positionCoordinate;
        this.coordinatesArray = coordinatesArray;
        this.coordinatesComparator = coordinatesComparator;
        this.flagsSelector = flagsSelector;

        gridSize = translation.gridSize;
    }

    @Override
    public void setView(TranslationsContract.View view) {
        this.view = view;
    }

    @Override
    public void start(Bundle savedInstanceState) {
        view.setCharacters(translation.characterList);
        view.setSourceFlag(flagsSelector.getFlagResource(translation.sourceLanguage));
        view.setTargetFlag(flagsSelector.getFlagResource(translation.targetLanguage));
        if (savedInstanceState != null) {
            solutions = savedInstanceState.getParcelableArrayList(KEY_SOLUTIONS);
        }
    }

    @Override
    public void resume() {
        verifySolutions();
        view.startListeningTouchEvents();
    }

    @Override
    public void pause() {
        view.stopListeningTouchEvents();
    }

    @Override
    public void onSavedInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY_SOLUTIONS, solutions);
    }

    @Override
    public void onCharacterTouched(int position, int eventAction) {
//        Log.d("zheng", "onCharacterTouched position:" + position + " pivotCharacterPosition:"
//                + pivotCharacterPosition + " eventAction:" + eventAction);
        if (eventAction == MotionEvent.ACTION_UP) {
            verifySolutions();
            clearSelection();
            return;
        }

        if (eventAction == MotionEvent.ACTION_OUTSIDE) {
            clearSelection();
            return;
        }

        if (eventAction == MotionEvent.ACTION_DOWN && isPositionInvalid()) {
            pivotCharacterPosition = position;
            pivotCoordinate = getCoordinatesFromPosition(position);
//            Log.d("zheng", "onCharacterTouched pivotCharacterPosition:" + pivotCharacterPosition);
        }

        pivotCharacterPosition = getPositionFromCoordinates(pivotCoordinate);
        if (position == pivotCharacterPosition) {
//            Log.d("zheng", "position == pivotCharacterPosition:" + pivotCharacterPosition);
            List<Integer> positions = new ArrayList<>();
            positions.add(pivotCharacterPosition);
            setSelectedItems(positions);
            return;
        }

        if (position != -1) {
//            Log.d("zheng", "calculateSelectedCharacters position:" + position + " pivotCharacterPosition:"
//                    + pivotCharacterPosition + " eventAction:" + eventAction);
            WordCoordinate newCoordinate = getCoordinatesFromPosition(position);
            calculateSelectedCharacters(newCoordinate);
        }
    }

    private void calculateSelectedCharacters(WordCoordinate coordinate) {
//        Log.d("zheng", "calculateSelectedCharacters coordinate:" + new Gson().toJson(coordinate)
//                        + " pivotCoordinate:" + new Gson().toJson(pivotCoordinate));
        if (coordinatesComparator.isCoordinateOnSameRow(pivotCoordinate, coordinate)) {
            selectItemsInRow(coordinate);
            return;
        }

        if (coordinatesComparator.isCoordinateOnSameColumn(pivotCoordinate, coordinate)) {
            selectItemsInColumn(coordinate);
            return;
        }

        selectItemsDiagonally(coordinate);
    }

    private boolean isPositionInvalid() {
        return pivotCharacterPosition == INVALID_FIRST_CHARACTER;
    }

    private void clearSelection() {
        lastPositionsSelection.clear();
        pivotCharacterPosition = INVALID_FIRST_CHARACTER;
        setSelectedItems(new ArrayList<>());
    }

    private void verifySolutions() {
        if (isSolutionAlreadyFound()) {
            return;
        }

        ArrayList<List<Integer>> solutionsPositions = new ArrayList<>();
        List<List<WordCoordinate>> allSolutionsCoordinates = translation.getAllSolutionsCoordinates();
        int size = allSolutionsCoordinates.size();
        for (int i = 0; i < size; i++) {
            solutionsPositions.add(getPositionsFromCoordinates(allSolutionsCoordinates.get(i)));
        }

        if (isSolutionValid(solutionsPositions)) {
            ArrayList<Integer> solutionList = new ArrayList<>(lastPositionsSelection);
            solutions.add(new Solution(solutionList));
        }

        setSolutionItems();

        int foundSolutions = solutions.size();
        int expectedSolutions = translation.locations.size();
        view.updateSolutionsRatio(foundSolutions, expectedSolutions);
        if (foundSolutions == expectedSolutions) {
            view.stopListeningTouchEvents();
            view.showNextButton();
        }
    }

    private Boolean isSolutionAlreadyFound() {
        for (Solution solution : solutions) {
            if (solution.positions.equals(lastPositionsSelection)) {
                return true;
            }
        }
        return false;
    }

    private Boolean isSolutionValid(List<List<Integer>> expectedSolutions) {
        for (List<Integer> expectedSolution : expectedSolutions) {
            if (expectedSolution.equals(lastPositionsSelection)) {
                return true;
            }
            Collections.reverse(expectedSolution);
            if (expectedSolution.equals(lastPositionsSelection)) {
                return true;
            }
        }
        return false;
    }

    private void selectItemsInRow(WordCoordinate coordinate) {
        List<WordCoordinate> coordinates = coordinatesArray.calculateCoordinatesOnSameRow(
                pivotCoordinate, coordinate);
        List<Integer> positions = getPositionsFromCoordinates(coordinates);
        setSelectedItems(positions);
    }

    private void selectItemsInColumn(WordCoordinate coordinate) {
        List<WordCoordinate> coordinates = coordinatesArray.calculateCoordinatesOnSameColumn(
                pivotCoordinate, coordinate);
        List<Integer> positions = getPositionsFromCoordinates(coordinates);
        setSelectedItems(positions);
    }

    private void selectItemsDiagonally(WordCoordinate coordinate) {
        List<WordCoordinate> coordinates = coordinatesArray.calculateCoordinatesDiagonally(
                pivotCoordinate, coordinate);
        List<Integer> positions = getPositionsFromCoordinates(coordinates);
        setSelectedItems(positions);
    }

    private List<Integer> getPositionsFromCoordinates(List<WordCoordinate> coordinates) {
        return positionCoordinate.positionsFromCoordinates(gridSize, coordinates);
    }

    private Integer getPositionFromCoordinates(WordCoordinate coordinates) {
        return positionCoordinate.positionFromCoordinate(gridSize, coordinates);
    }

    private WordCoordinate getCoordinatesFromPosition(Integer position) {
        return positionCoordinate.coordinateFromPosition(gridSize, position);
    }

    private void setSelectedItems(List<Integer> positions) {
        lastPositionsSelection.clear();
        lastPositionsSelection.addAll(positions);
        view.setSelectedItems(positions);
    }

    private void setSolutionItems() {
        ArrayList<Integer> solutionItems = new ArrayList<>();
        int solutionSize = solutions.size();
        for (int i = 0; i < solutionSize; i++) {
            solutionItems.addAll(solutions.get(i).positions);
        }
        view.setSolutionItems(solutionItems);
    }

}