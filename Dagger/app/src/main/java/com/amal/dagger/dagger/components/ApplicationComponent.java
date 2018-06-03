package com.amal.dagger.dagger.components;

import com.amal.dagger.dagger.modules.ApplicationModule;
import com.amal.dagger.dagger.modules.ControllerModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    ControllerComponent newControllerComponent(ControllerModule module);

}
