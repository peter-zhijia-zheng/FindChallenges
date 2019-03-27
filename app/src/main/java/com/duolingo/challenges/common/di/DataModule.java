package com.duolingo.challenges.common.di;


import com.duolingo.challenges.data.local.TranslationsStore;

import dagger.Module;
import dagger.Provides;

/* Module that contains dependencies to access local data. */
@Module
public class DataModule {

    @Provides
    @ApplicationScope
    public TranslationsStore providesTranslationStore() {
        return new TranslationsStore();
    }

}