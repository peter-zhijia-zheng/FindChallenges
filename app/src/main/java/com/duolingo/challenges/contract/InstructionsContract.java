package com.duolingo.challenges.contract;

import android.support.annotation.StringRes;

import com.duolingo.challenges.mvp.BasePresenter;
import com.duolingo.challenges.mvp.BaseView;

public interface InstructionsContract {

    interface Presenter extends BasePresenter<View> {

        void start();

    }

    interface View extends BaseView {

        void setContinueButtonEnabled(Boolean enabled);

        void showMessage(@StringRes int messageRes);

    }

}