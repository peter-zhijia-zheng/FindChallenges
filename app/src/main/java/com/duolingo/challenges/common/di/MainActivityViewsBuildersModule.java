package com.duolingo.challenges.common.di;

import com.duolingo.challenges.ui.InstructionsFragment;
import com.duolingo.challenges.ui.MainActivity;
import com.duolingo.challenges.ui.ResultFragment;
import com.duolingo.challenges.ui.TranslationsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
@ActivityScope
public abstract class MainActivityViewsBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract InstructionsFragment contributeInstructionsFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract TranslationsFragment contributeTranslationsFragmentInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract ResultFragment contributeResultFragmentInjector();

}