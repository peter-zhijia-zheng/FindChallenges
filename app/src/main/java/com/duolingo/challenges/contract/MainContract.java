package com.duolingo.challenges.contract;

import android.os.Bundle;
import android.support.annotation.StringRes;

import com.duolingo.challenges.common.extention.AnimationType;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.mvp.BasePresenter;
import com.duolingo.challenges.mvp.BaseView;

public interface MainContract {

    interface Presenter extends BasePresenter<View> {

        void start(Bundle savedInstanceState);

        void onRestoreInstanceState(Bundle savedInstanceState);

        void onSavedInstanceState(Bundle outBundle);

        void onInstructionsReady();

        void onGridCompleted();

        void onRestartRequested();

        void onFinishRequested();

    }

    interface View extends BaseView {

        void setTitle(@StringRes int titleResource);

        void showInstructions(AnimationType animationType);

        void showTranslation(Translation translation, AnimationType animationType);

        void showResult(AnimationType animationType);

        void updateProgress(int maxValue, int currentValue);

        void showMessage(@StringRes int messageRes);

        void finish();

    }

}