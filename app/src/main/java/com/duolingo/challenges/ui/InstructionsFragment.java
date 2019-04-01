package com.duolingo.challenges.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.duolingo.challenges.R;
import com.duolingo.challenges.contract.InstructionsContract;
import com.duolingo.challenges.presenter.InstructionsPresenter;
import com.duolingo.challenges.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class InstructionsFragment extends BaseFragment implements
        InstructionsContract.View {

    @Inject
    InstructionsPresenter presenter;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    private FragmentContainer container;

    public static Fragment newInstance() {
        return new InstructionsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment();
        container = (FragmentContainer) (parentFragment == null ? context : parentFragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        @SuppressWarnings("Annotator") View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        bindView(this, view);

        presenter.setView(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.start();
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void setContinueButtonEnabled(Boolean enabled) {
        btnContinue.setEnabled(enabled);
    }

    @Override
    public void showMessage(int messageRes) {
        container.showMessage(messageRes);
    }

    @OnClick(R.id.btn_continue)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_continue:
                container.onInstructionsReady();
                break;
        }
    }

    interface FragmentContainer {

        void onInstructionsReady();

        void showMessage(@StringRes int messageRes);

    }

}