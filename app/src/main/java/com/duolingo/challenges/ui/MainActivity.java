package com.duolingo.challenges.ui;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.duolingo.challenges.R;
import com.duolingo.challenges.common.extention.AnimationType;
import com.duolingo.challenges.common.extention.FragmentManagerExtensions;
import com.duolingo.challenges.contract.MainContract;
import com.duolingo.challenges.data.models.Translation;
import com.duolingo.challenges.presenter.MainPresenter;
import com.duolingo.challenges.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements
        MainContract.View,
        InstructionsFragment.FragmentContainer,
        TranslationsFragment.FragmentContainer,
        ResultFragment.FragmentContainer {

    private static final int ANIMATION_MULTIPLIER = 20;
    private static final String PROGRESS_PROPERTY = "progress";
    private static final long PROGRESS_ANIMATION_DURATION_MS = 500L;

    @Inject
    MainPresenter presenter;

    @Inject
    FragmentManagerExtensions fragmentManagerExtensions;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pb_progress_indicator)
    ProgressBar pbProgressIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        presenter.setView(this);
        presenter.start(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        presenter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSavedInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void showInstructions(AnimationType animationType) {
        showFragment(InstructionsFragment.newInstance(), AnimationType.NONE);
    }

    @Override
    public void showTranslation(Translation translation, AnimationType animationType) {
        showFragment(TranslationsFragment.newInstance(translation), AnimationType.SLIDE);
    }

    @Override
    public void showResult(AnimationType animationType) {
        showFragment(ResultFragment.newInstance(), AnimationType.NONE);
    }

    @Override
    public void updateProgress(int maxValue, int currentValue) {
        pbProgressIndicator.setMax(maxValue * ANIMATION_MULTIPLIER);
        int currentProgress = pbProgressIndicator.getProgress();
        int newProgress = currentValue * ANIMATION_MULTIPLIER;
        ObjectAnimator animator = ObjectAnimator.ofInt(
                pbProgressIndicator, PROGRESS_PROPERTY, currentProgress, newProgress);
        animator.setDuration(PROGRESS_ANIMATION_DURATION_MS);
        animator.start();
    }

    @Override
    public void showMessage(@StringRes int messageRes) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInstructionsReady() {
        presenter.onInstructionsReady();
    }

    @Override
    public void onGridCompleted() {
        presenter.onGridCompleted();
    }

    @Override
    public void onRestartRequested() {
        presenter.onRestartRequested();
    }

    @Override
    public void onFinishRequested() {
        presenter.onFinishRequested();
    }

//    private void showFragment(Fragment fragment, AnimationType animationType) {
//        replaceWithoutBackStack(R.id.fragment_container, fragment, animationType);
//    }

    private void showFragment(Fragment fragment, AnimationType animationType) {
        fragmentManagerExtensions.replaceWithoutBackStack(getSupportFragmentManager(),
                R.id.fragment_container, fragment, animationType);
    }


//    private void replaceWithoutBackStack(int layoutId,
//                                         Fragment fragment,
//                                         AnimationType animationType) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        if (animationType == AnimationType.SLIDE) {
//            transaction.setCustomAnimations(
//                    R.anim.open_from_right,
//                    R.anim.close_to_left,
//                    R.anim.open_from_left,
//                    R.anim.close_to_right);
//        } else if (animationType == AnimationType.FADE) {
//            transaction.setCustomAnimations(
//                    android.R.anim.fade_in,
//                    android.R.anim.fade_out,
//                    android.R.anim.fade_in,
//                    android.R.anim.fade_out);
//        }
//
//        transaction.replace(layoutId, fragment);
//        transaction.commit();
//    }
}
