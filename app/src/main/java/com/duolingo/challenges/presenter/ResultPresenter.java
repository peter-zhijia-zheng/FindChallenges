package com.duolingo.challenges.presenter;

import com.duolingo.challenges.contract.ResultContract;

import javax.inject.Inject;

public class ResultPresenter extends ReactivePresenter implements ResultContract.Presenter {
    private ResultContract.View view;

    @Inject
    public ResultPresenter() {
        super();
    }

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