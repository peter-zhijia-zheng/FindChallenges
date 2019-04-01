package com.duolingo.challenges.presenter;

import android.os.Bundle;

import com.duolingo.challenges.contract.MainContract;
import com.duolingo.challenges.R;
import com.duolingo.challenges.common.extention.AnimationType;
import com.duolingo.challenges.data.local.TranslationsStore;
import com.duolingo.challenges.data.models.Translation;

import javax.inject.Inject;

public class MainPresenter extends ReactivePresenter implements MainContract.Presenter {
    public static final String KEY_TRANSLATION_INDEX = "KEY_TRANSLATION_INDEX";
    public static final int DEFAULT_PROGRESS_MAX_VALUE = 1;
    public static final int DEFAULT_PROGRESS_VALUE = 0;
    public static final int DEFAULT_INDEX = 0;

    private TranslationsStore translationsStore;
    private MainContract.View view;

    public int translationIndex = DEFAULT_INDEX;

    @Inject
    public MainPresenter(TranslationsStore translationsStore) {
        this.translationsStore = translationsStore;
    }

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void start(Bundle savedInstanceState) {
        view.setTitle(R.string.app_name);

        if (savedInstanceState == null || !translationsStore.hasTranslations()) {
            view.updateProgress(DEFAULT_PROGRESS_MAX_VALUE, DEFAULT_PROGRESS_VALUE);
            view.showInstructions(AnimationType.NONE);
            return;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            translationIndex = savedInstanceState.getInt(KEY_TRANSLATION_INDEX);
        }
    }

    @Override
    public void onSavedInstanceState(Bundle outBundle) {
        if (outBundle != null) {
            outBundle.putInt(KEY_TRANSLATION_INDEX, translationIndex);
        }
    }

    @Override
    public void onInstructionsReady() {
        showTranslationGrid();
        view.updateProgress(translationsStore.getTranslationCount(), translationIndex);
    }

    @Override
    public void onGridCompleted() {
        translationIndex++;
        int translationCount = translationsStore.getTranslationCount();
        view.updateProgress(translationCount, translationIndex);

        if (translationIndex >= translationCount) {
            view.showResult(AnimationType.SLIDE);
        } else {
            showTranslationGrid();
        }
    }

    @Override
    public void onRestartRequested() {
        translationsStore.clearTranslations();
        translationIndex = DEFAULT_INDEX;
        view.showInstructions(AnimationType.FADE);
        view.updateProgress(DEFAULT_PROGRESS_MAX_VALUE, DEFAULT_PROGRESS_VALUE);
    }

    @Override
    public void onFinishRequested() {
        view.finish();
    }

    private void showTranslationGrid() {
        Translation translation = translationsStore.fetchTranslationWithIndex(translationIndex);
        if (translation != null) {
            view.showTranslation(translation, AnimationType.SLIDE);
        }
    }

}