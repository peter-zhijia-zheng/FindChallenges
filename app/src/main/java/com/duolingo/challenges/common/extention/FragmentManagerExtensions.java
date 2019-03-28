package com.duolingo.challenges.common.extention;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.duolingo.challenges.R;

import javax.inject.Inject;

public class FragmentManagerExtensions {
    @Inject
    public FragmentManagerExtensions() {

    }

    public void replaceWithoutBackStack(FragmentManager fragmentManager,
                                        int layoutId,
                                        Fragment fragment,
                                        AnimationType animationType) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (animationType == AnimationType.SLIDE) {
            transaction.setCustomAnimations(
                    R.anim.open_from_right,
                    R.anim.close_to_left,
                    R.anim.open_from_left,
                    R.anim.close_to_right);
        } else if (animationType == AnimationType.FADE) {
            transaction.setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out);
        }

        transaction.replace(layoutId, fragment);
        transaction.commit();
    }
}
