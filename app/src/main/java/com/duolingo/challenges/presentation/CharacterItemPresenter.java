package com.duolingo.challenges.presentation;

import com.duolingo.challenges.R;
import com.duolingo.challenges.contract.CharacterItemContract;
import com.duolingo.challenges.usecases.CharacterTextSizeUseCase;

import java.util.List;

public class CharacterItemPresenter extends ReactivePresenter implements CharacterItemContract.Presenter {

    private CharacterItemContract.View view;

    private final int gridSize;
    private CharacterTextSizeUseCase characterTextSizeUseCase;

    public CharacterItemPresenter(int gridSize, CharacterTextSizeUseCase characterTextSizeUseCase) {
        this.gridSize = gridSize;
        this.characterTextSizeUseCase = characterTextSizeUseCase;
    }

    @Override
    public void setView(CharacterItemContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.setCharacterTextSize(characterTextSizeUseCase.getTextSizeBasedOnGrid(gridSize));
    }

    @Override
    public void onCharacterUpdated(String character,
                                   int position,
                                   List<Integer> selectedItems,
                                   List<Integer> solutionItems) {
        view.setCharacterText(character);

        if (selectedItems.contains(position)) {
            view.showSelector();
            view.setCharacterTextColor(android.R.color.white);
        } else {
            view.hideSelector();
            view.setCharacterTextColor(android.R.color.black);
        }

        if (solutionItems.contains(position)) {
            view.setCellBackgroundColor(R.color.colorSolution);
        } else {
            view.setCellBackgroundColor(android.R.color.transparent);
        }
    }

}