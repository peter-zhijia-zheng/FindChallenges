package com.duolingo.challenges.presenter;

import com.duolingo.challenges.R;
import com.duolingo.challenges.contract.CharacterItemContract;
import com.duolingo.challenges.presenter.CharacterItemPresenter;
import com.duolingo.challenges.usecases.CharacterTextSizeUseCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CharacterItemPresenterTest {
    private static final int GRID_SIZE = 5;
    private static final int TEXT_SIZE = 15;
    private static final String TEXT = "text";
    private static final int POSITION = 1;
            
    @Mock
    private CharacterTextSizeUseCase characterTextSizeUseCase;
    
    @Mock
    private CharacterItemContract.View view;

    private CharacterItemPresenter presenter;

    private List<Integer> selectedItems = new ArrayList<>();
    private List<Integer> solutionItems = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new CharacterItemPresenter(
                GRID_SIZE,
                characterTextSizeUseCase);
        presenter.setView(view);
    }

    @After
    public void tearDown() {
        selectedItems.clear();
        solutionItems.clear();
    }

    @Test
    public void start_setsCharacterTextSize() {
        when(characterTextSizeUseCase.getTextSizeBasedOnGrid(GRID_SIZE)).thenReturn(TEXT_SIZE);

        presenter.start();

        verify(view).setCharacterTextSize(TEXT_SIZE);
    }

    @Test
    public void characterUpdated_setCharacterText() {
        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems);

        verify(view).setCharacterText(TEXT);
    }

    @Test
    public void characterUpdatedAndSelected_showSelected() {
        selectedItems.add(POSITION);

        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems);

        verify(view).showSelector();
    }

    @Test
    public void characterUpdatedAndSelected_setWhiteTextColor() {
        selectedItems.add(POSITION);

        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems);

        verify(view).setCharacterTextColor(android.R.color.white);
    }

    @Test
    public void characterUpdatedAndSelectedOutOfGrid_hideSelected() {
        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems);

        verify(view).hideSelector();
    }

    @Test
    public void characterUpdatedAndSelectedOutOfGrid_setBlackTextColor() {
        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems);

        verify(view).setCharacterTextColor(android.R.color.black);
    }

    @Test
    public void characterUpdatedAndSelected_setCellColorToExpected() {
        solutionItems.add(POSITION);

        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems);

        verify(view).setCellBackgroundColor(R.color.colorSolution);
    }

    @Test
    public void characterUpdatedAndSolutionDoesNotContainPosition_setCellColorToTransparent() {
        presenter.onCharacterUpdated(TEXT, POSITION, selectedItems, solutionItems);

        verify(view).setCellBackgroundColor(android.R.color.transparent);
    }
}