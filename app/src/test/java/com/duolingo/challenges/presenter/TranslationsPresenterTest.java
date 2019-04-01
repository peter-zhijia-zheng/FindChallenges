package com.duolingo.challenges.presenter;

import android.os.Bundle;
import android.view.MotionEvent;

import com.duolingo.challenges.contract.TranslationsContract;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.data.models.WordLocation;
import com.duolingo.challenges.usecases.CoordinatesArrayUseCase;
import com.duolingo.challenges.usecases.CoordinatesComparatorUseCase;
import com.duolingo.challenges.usecases.FlagSelectorUseCase;
import com.duolingo.challenges.usecases.PositionCoordinateUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

public class TranslationsPresenterTest {
    private static final int POSITION = 0;

    @Mock
    private Translation translation;
    @Mock
    private PositionCoordinateUseCase positionCoordinate;
    @Mock
    private CoordinatesArrayUseCase coordinatesArray;
    @Mock
    private CoordinatesComparatorUseCase coordinatesComparator;
    @Mock
    private FlagSelectorUseCase flagsSelector;
    @Mock
    private Bundle savedInstanceState;
    @Mock
    private TranslationsContract.View view;

    @Mock
    private List<String> characterList;
    @Mock
    private List<WordLocation> locations;

    private TranslationsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        translation.characterList = characterList;
        translation.locations = locations;
        presenter = new TranslationsPresenter(
                translation,
                positionCoordinate,
                coordinatesArray,
                coordinatesComparator,
                flagsSelector);
        presenter.setView(view);
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

    @SuppressWarnings("unchecked")
    @Test
    public void characterTouchedAndActionUp_setEmptySelectedItemList() {
        presenter.onCharacterTouched(POSITION, MotionEvent.ACTION_UP);

        ArgumentCaptor<List<Integer>> listCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).setSelectedItems(listCaptor.capture());
        assertEquals(listCaptor.getValue().size(), 0);
    }

}
