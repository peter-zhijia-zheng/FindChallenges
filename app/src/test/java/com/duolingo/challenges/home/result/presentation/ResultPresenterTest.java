package com.duolingo.challenges.home.result.presentation;


import com.duolingo.challenges.contract.ResultContract;
import com.duolingo.challenges.presentation.ResultPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class ResultPresenterTest {

    @Mock
    private ResultContract.View view;

    private ResultPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new ResultPresenter();
        presenter.setView(view);
    }

    @Test
    public void clickFinish_finishOnView() {
        presenter.onFinishClicked();

        verify(view).onFinishRequested();
    }

    @Test
    public void clickRestart_restartOnView() {
        presenter.onRestartClicked();

        verify(view).onRestartRequested();
    }

}