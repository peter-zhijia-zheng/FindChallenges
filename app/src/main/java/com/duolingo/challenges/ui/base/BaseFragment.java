package com.duolingo.challenges.ui.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbindView();
    }

    protected void bindView(Object target, View source) {
        unbinder = ButterKnife.bind(target, source);
    }

    private void unbindView() {
        unbinder.unbind();
    }

}