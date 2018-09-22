package com.amal.dagger.dagger.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class AppContextModule {

    private final Application application;

    public AppContextModule(Application application) {
        this.application = application;
    }


    @Named("application_context")
    @Provides
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }


}
