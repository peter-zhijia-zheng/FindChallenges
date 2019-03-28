package com.duolingo.challenges.home.translations.presentation;

import android.os.Bundle;
import android.view.MotionEvent;

import com.duolingo.challenges.contract.TranslationsContract;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.presentation.TranslationsPresenter;
import com.duolingo.challenges.usecases.CoordinatesArrayUseCase;
import com.duolingo.challenges.usecases.CoordinatesComparatorUseCase;
import com.duolingo.challenges.usecases.FlagSelectorUseCase;
import com.duolingo.challenges.usecases.PositionCoordinateUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TranslationsPresenterTest {
    private static final int SOURCE_FLAG_RES = 1;
    private static final String SOURCE_LANGUAGE = "source";
    private static final int TARGET_FLAG_RES = 2;
    private static final String TARGET_LANGUAGE = "target";
    private static final int POSITION = 0;
            
    @Mock
    private Translation translation;
    @Mock private PositionCoordinateUseCase positionCoordinate;
    @Mock private CoordinatesArrayUseCase coordinatesArray;
    @Mock private CoordinatesComparatorUseCase coordinatesComparator;
    @Mock private FlagSelectorUseCase flagsSelector;
    @Mock private Bundle savedInstanceState;
    @Mock private TranslationsContract.View view;

    private TranslationsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new TranslationsPresenter(
                translation,
                positionCoordinate,
                coordinatesArray,
                coordinatesComparator,
                flagsSelector);
        presenter.setView(view);
    }

    @Test
    public void start_setCharactersInView() {
        List<String> characters = new ArrayList<>();
        when(translation.characterList).thenReturn(characters);

        presenter.start(savedInstanceState);

        verify(view).setCharacters(characters);
    }

    @Test
    public void start_setSourceFlagInView() {
        when(translation.sourceLanguage).thenReturn(SOURCE_LANGUAGE);
        when(flagsSelector.getFlagResource(SOURCE_LANGUAGE)).thenReturn(SOURCE_FLAG_RES);

        presenter.start(savedInstanceState);

        verify(view).setSourceFlag(SOURCE_FLAG_RES);
    }

    @Test
    public void start_setTargetFlagInView() {
        when(translation.targetLanguage).thenReturn(TARGET_LANGUAGE);
        when(flagsSelector.getFlagResource(TARGET_LANGUAGE)).thenReturn(TARGET_FLAG_RES);

        presenter.start(savedInstanceState);

        verify(view).setTargetFlag(TARGET_FLAG_RES);
    }

    @Test
    public void resume_listenToTouchEvents() {
        presenter.resume();

        verify(view).startListeningTouchEvents();
    }

    @Test
    public void pause_stopListenToTouchEvents() {
        presenter.resume();

        verify(view).stopListeningTouchEvents();
    }

    @Test
    public void characterTouchedAndActionUp_setEmptySelectedItemList() {
        presenter.onCharacterTouched(POSITION, MotionEvent.ACTION_UP);

        ArgumentCaptor<List<Integer>> listCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).setSelectedItems(listCaptor.capture());
        assertEquals(listCaptor.getValue().size(), 0);
    }
    
}