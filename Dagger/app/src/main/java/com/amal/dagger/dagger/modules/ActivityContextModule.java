package com.amal.dagger.dagger.modules;

import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityContextModule {

    private final Activity activity;

    public ActivityContextModule(Activity activity) {
        this.activity = activity;
    }

    @Named("activity_context")
    @Provides
    Context provideApplicationContext() {
        return activity;
    }
}
