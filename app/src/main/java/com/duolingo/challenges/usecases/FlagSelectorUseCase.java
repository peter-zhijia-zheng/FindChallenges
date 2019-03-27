package com.duolingo.challenges.usecases;

import android.support.annotation.DrawableRes;

import com.duolingo.challenges.R;

public class FlagSelectorUseCase {

    @DrawableRes
    public int getFlagResource(String key) {
        switch (key) {
            case "en" :
                return R.drawable.ic_flag_en;
            case "es" :
                return R.drawable.ic_flag_es;
            default:
                return R.drawable.ic_flag_en;
        }
    }
}