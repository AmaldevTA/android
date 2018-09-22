package com.amal.dagger.main;

import com.amal.dagger.dagger.ApplicationComponent;
import com.amal.dagger.retrofit.APIComponent;

import dagger.Component;

@Component(modules = MainActivityModule.class, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {
    APIComponent getApiComponent();

    Adapter getAdapter();

    void injectMainActivity(MainActivity mainActivity);
}
