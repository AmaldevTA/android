package com.amal.dagger;

import android.app.Application;

import com.amal.dagger.dagger.components.ApplicationComponent;
import com.amal.dagger.dagger.components.DaggerApplicationComponent;
import com.amal.dagger.dagger.modules.ApplicationModule;


public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


}
