package com.duolingo.challenges.contract;

import android.os.Bundle;
import android.support.annotation.DrawableRes;

import com.duolingo.challenges.presentation.BasePresenter;

import java.util.List;

public interface TranslationsContract {

    interface Presenter extends BasePresenter<View> {

        void start(Bundle savedInstanceState);

        void resume();

        void pause();

        void onSavedInstanceState(Bundle outState);

        void onCharacterTouched(int position, int eventAction);

    }

    interface View extends BaseView {

        void startListeningTouchEvents();

        void stopListeningTouchEvents();

        void setCharacters(List<String> characters);

        void setSourceFlag(@DrawableRes int resource);

        void setTargetFlag(@DrawableRes int resource);

        void setSelectedItems(List<Integer> positions);

        void setSolutionItems(List<Integer> positions);

        void updateSolutionsRatio(int found, int expected);

        void showNextButton();

    }

}