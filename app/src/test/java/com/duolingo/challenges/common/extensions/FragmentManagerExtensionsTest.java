package com.duolingo.challenges.common.extensions;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.duolingo.challenges.R;
import com.duolingo.challenges.common.extention.AnimationType;
import com.duolingo.challenges.common.extention.FragmentManagerExtensions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressLint("CommitTransaction")
public class FragmentManagerExtensionsTest {
    private static final int LAYOUT_ID = 9874;
    private static final AnimationType ANIMATION_TYPE = AnimationType.FADE;

    @Mock
    private FragmentManager manager;
    @Mock private FragmentTransaction transaction;
    @Mock private Fragment fragment;
    @Mock private FragmentManagerExtensions fragmentManagerExtensions;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(manager.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void replaceWithoutBackStack_replacesFragmentAndCommitChange() {
        fragmentManagerExtensions.replaceWithoutBackStack(manager, LAYOUT_ID, fragment, ANIMATION_TYPE);

        verify(transaction).replace(LAYOUT_ID, fragment);
        verify(transaction).commit();
    }

    @Test
    public void replaceWithoutBackStackWithNoneAnimation_setsNoCustomAnimations() {
        fragmentManagerExtensions.replaceWithoutBackStack(manager, LAYOUT_ID, fragment, AnimationType.NONE);

        verify(transaction, never()).setCustomAnimations(any(), any(), any(), any());
    }

    @Test
    public void replaceWithoutBackStackWithSlideAnimation_setsCustomAnimations() {
        fragmentManagerExtensions.replaceWithoutBackStack(manager, LAYOUT_ID, fragment, AnimationType.SLIDE);

        verify(transaction).setCustomAnimations(
                R.anim.open_from_right,
                R.anim.close_to_left,
                R.anim.open_from_left,
                R.anim.close_to_right);
    }

    @Test
    public void replaceWithoutBackStackWithFadeAnimation_setsCustomAnimations() {
        fragmentManagerExtensions.replaceWithoutBackStack(manager, LAYOUT_ID, fragment, AnimationType.FADE);

        verify(transaction).setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

}