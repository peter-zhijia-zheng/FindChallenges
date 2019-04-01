package com.duolingo.challenges.contract;

import com.duolingo.challenges.presenter.BasePresenter;

public interface ResultContract {

    interface Presenter extends BasePresenter<View> {

        void onFinishClicked();

        void onRestartClicked();

    }

    interface View extends BaseView {

        void onRestartRequested();

        void onFinishRequested();

    }

}