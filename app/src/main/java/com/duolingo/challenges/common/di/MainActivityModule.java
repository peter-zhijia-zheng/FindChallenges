package com.duolingo.challenges.common.di;


import android.app.Activity;

import com.duolingo.challenges.ui.MainActivity;

import dagger.Module;
import dagger.Provides;

/* Module that contains specific dependencies for the Main Activity. */
@Module
@ActivityScope
public class MainActivityModule {

    @Provides
    @ActivityScope Activity providesActivity(
            MainActivity activity
    ) {
        return activity;
    }

}
