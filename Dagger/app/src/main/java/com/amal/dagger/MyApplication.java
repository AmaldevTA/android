package com.amal.dagger;

import android.app.Application;

import com.amal.dagger.dagger.ApplicationComponent;
import com.amal.dagger.dagger.DaggerApplicationComponent;
import com.amal.dagger.dagger.modules.AppContextModule;


public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .appContextModule(new AppContextModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


}
