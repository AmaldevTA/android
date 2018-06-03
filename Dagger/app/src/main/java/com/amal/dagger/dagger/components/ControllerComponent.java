package com.amal.dagger.dagger.components;


import com.amal.dagger.MainActivity;
import com.amal.dagger.dagger.modules.ControllerModule;

import dagger.Subcomponent;


@Subcomponent(modules = {ControllerModule.class})
public interface ControllerComponent {

    void inject(MainActivity activity);
}
