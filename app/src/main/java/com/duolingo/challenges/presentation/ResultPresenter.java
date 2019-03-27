package com.duolingo.challenges.presentation;

import com.duolingo.challenges.contract.ResultContract;
import com.duolingo.challenges.mvp.ReactivePresenter;

import javax.inject.Inject;

public class ResultPresenter extends ReactivePresenter implements ResultContract.Presenter {
    @Inject
    public ResultPresenter() {
        super();
    }

    private ResultContract.View view;

    @Override
    public void setView(ResultContract.View view) {
        this.view = view;
    }

    @Override
    public void onFinishClicked() {
        view.onFinishRequested();
    }

    @Override
    public void onRestartClicked() {
        view.onRestartRequested();
    }

}