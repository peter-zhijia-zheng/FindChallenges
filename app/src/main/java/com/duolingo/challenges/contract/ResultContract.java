package com.duolingo.challenges.contract;

import com.duolingo.challenges.mvp.BasePresenter;
import com.duolingo.challenges.mvp.BaseView;

public interface ResultContract {

    interface Presenter extends BasePresenter<View>

    {

        public void onFinishClicked();

        public void onRestartClicked();

    }

    interface View extends BaseView

    {

        public void onRestartRequested();

        public void onFinishRequested();

    }

}