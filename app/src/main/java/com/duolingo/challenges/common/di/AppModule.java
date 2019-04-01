package com.duolingo.challenges.common.di;

import android.app.Application;
import android.content.Context;

import com.duolingo.challenges.common.schedulers.RealSchedulerProvider;
import com.duolingo.challenges.common.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/* Module that contains generic android dependencies. */
@Module
class AppModule {

    @Provides
    @ApplicationScope
    public Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @ApplicationScope
    public SchedulerProvider provideSchedulerProvider() {
        return new RealSchedulerProvider();
    }
}