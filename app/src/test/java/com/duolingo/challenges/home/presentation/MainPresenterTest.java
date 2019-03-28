package com.duolingo.challenges.home.presentation;


import android.os.Bundle;

import com.duolingo.challenges.R;
import com.duolingo.challenges.common.extention.AnimationType;
import com.duolingo.challenges.contract.MainContract;
import com.duolingo.challenges.data.local.TranslationsStore;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.presentation.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.duolingo.challenges.presentation.MainPresenter.DEFAULT_INDEX;
import static com.duolingo.challenges.presentation.MainPresenter.DEFAULT_PROGRESS_MAX_VALUE;
import static com.duolingo.challenges.presentation.MainPresenter.DEFAULT_PROGRESS_VALUE;
import static com.duolingo.challenges.presentation.MainPresenter.KEY_TRANSLATION_INDEX;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    @Mock
    private TranslationsStore translationsStore;
    @Mock private MainContract.View view;
    @Mock private Bundle savedInstanceState;
    @Mock private Translation translation;

    private MainPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter(translationsStore);
        presenter.setView(view);
    }

    @Test
    public void start_setExpectedTitle() {
        presenter.start(savedInstanceState);

        verify(view).setTitle(R.string.app_name);
    }

    @Test
    public void startedWithNullBundleAndNoTranslations_updatesProgressWithDefault() {
        presenter.start(null);

        verify(view).updateProgress(DEFAULT_PROGRESS_MAX_VALUE, DEFAULT_PROGRESS_VALUE);
    }

    @Test
    public void startedWithNullBundleAndNoTranslations_showsInstructions() {
        presenter.start(null);

        verify(view).showInstructions(AnimationType.NONE);
    }

    @Test
    public void restoreInstanceState_setValueFromBundle() {
        int index = 5;
        when(savedInstanceState.getInt(KEY_TRANSLATION_INDEX)).thenReturn(index);

        presenter.onRestoreInstanceState(savedInstanceState);

        assertEquals(presenter.translationIndex, index);
    }

    @Test
    public void saveInstanceState_storesIndexInBundle() {
        presenter.onSavedInstanceState(savedInstanceState);

        verify(savedInstanceState).putInt(KEY_TRANSLATION_INDEX, presenter.translationIndex);
    }

    @Test
    public void instructionsReady_showsCharacterGrid() {
        when(translationsStore.fetchTranslationWithIndex(any())).thenReturn(translation);

        presenter.onInstructionsReady();

        verify(view).showTranslation(translation, AnimationType.SLIDE);
    }

    @Test
    public void instructionsReady_updatesProgress() {
        int index = presenter.translationIndex;
        int count = 5;
        when(translationsStore.getTranslationCount()).thenReturn(count);

        presenter.onInstructionsReady();

        verify(view).updateProgress(count, index);
    }

    @Test
    public void restartRequested_clearsTranslationFromStore() {
        presenter.onRestartRequested();

        verify(translationsStore).clearTranslations();
    }

    @Test
    public void restartRequested_resetsIndexToDefault() {
        presenter.onRestartRequested();

        assertEquals(presenter.translationIndex, DEFAULT_INDEX);
    }

    @Test
    public void restartRequested_showsInstructions() {
        presenter.onRestartRequested();

        verify(view).showInstructions(AnimationType.FADE);
    }

    @Test
    public void restartRequested_updatesProgressWithDefault() {
        presenter.onRestartRequested();

        verify(view).updateProgress(DEFAULT_PROGRESS_MAX_VALUE, DEFAULT_PROGRESS_VALUE);
    }

    @Test
    public void finish_callsFinishOnView() {
        presenter.onFinishRequested();

        verify(view).finish();
    }
}