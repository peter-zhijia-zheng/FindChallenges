package com.duolingo.challenges.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duolingo.challenges.R;
import com.duolingo.challenges.contract.ResultContract;
import com.duolingo.challenges.presenter.ResultPresenter;
import com.duolingo.challenges.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.OnClick;

public class ResultFragment extends BaseFragment implements
        ResultContract.View {

    public static Fragment newInstance() {
        return new ResultFragment();
    }

    @Inject
    ResultPresenter presenter;

    private FragmentContainer container;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        container = (FragmentContainer) (parentFragment == null ? context : parentFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        bindView(this, view);

        presenter.setView(this);

        return view;
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @OnClick({R.id.btn_restart, R.id.btn_finish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_restart:
                presenter.onRestartClicked();
                break;
            case R.id.btn_finish:
                presenter.onFinishClicked();
                break;
        }
    }

    @Override
    public void onRestartRequested() {
        container.onRestartRequested();
    }

    @Override
    public void onFinishRequested() {
        container.onFinishRequested();
    }

    interface FragmentContainer {

        void onRestartRequested();

        void onFinishRequested();

    }

}