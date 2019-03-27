package com.duolingo.challenges.usecases;

import android.support.annotation.DimenRes;

import com.duolingo.challenges.R;

public class CharacterTextSizeUseCase {

    @DimenRes
    public Integer getTextSizeBasedOnGrid(Integer gridSize) {
        if (gridSize <= 5) return R.dimen.two_and_three_quarter_text_unit;
        switch (gridSize) {
            case 6:
                return R.dimen.two_and_half_text_unit;
            case 7:
                return R.dimen.two_and_quarter_text_unit;
            case 8:
                return R.dimen.two_text_unit;
            default:
                return R.dimen.one_and_three_quarter_text_unit;
        }
    }
}