package com.duolingo.challenges.contract;

import android.support.annotation.StringRes;
import com.duolingo.challenges.mvp.BasePresenter;
import com.duolingo.challenges.mvp.BaseView;

public interface InstructionsContract {

    interface Presenter extends BasePresenter<View> {

        public void start();

    }

    interface View extends BaseView {

        public void setContinueButtonEnabled(Boolean enabled);

        public void showMessage(@StringRes int messageRes);

    }

}