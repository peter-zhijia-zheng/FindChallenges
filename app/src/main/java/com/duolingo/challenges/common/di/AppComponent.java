package com.duolingo.challenges.common.di;


/**
 * Application component refers to application level modules only.
 */

import android.app.Application;

import com.duolingo.challenges.WordsApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@ApplicationScope
@Component(modules = {AndroidSupportInjectionModule.class,
        AppModule.class,
        DataModule.class,
        NetworkModule.class,
        MainActivityViewsBuildersModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(WordsApplication application);
}