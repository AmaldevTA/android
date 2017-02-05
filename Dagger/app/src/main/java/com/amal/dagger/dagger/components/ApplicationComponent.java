package com.amal.dagger.dagger.components;


import com.amal.dagger.MainActivity;
import com.amal.dagger.dagger.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity activity);
}
