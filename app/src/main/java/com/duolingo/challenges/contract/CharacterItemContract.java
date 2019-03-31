package com.duolingo.challenges.contract;

import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;

import com.duolingo.challenges.presentation.BasePresenter;

import java.util.List;

public interface CharacterItemContract {

    interface Presenter extends BasePresenter<View> {

        void start();

        void onCharacterUpdated(String character,
                                int position,
                                List<Integer> selectedItems,
                                List<Integer> solutionItems);

    }

    interface View extends BaseView {

        void setCharacterTextSize(@DimenRes int textSize);

        void setCharacterText(String text);

        void setCharacterTextColor(@ColorRes int colorRes);

        void showSelector();

        void hideSelector();

        void setCellBackgroundColor(@ColorRes int colorRes);

    }

}