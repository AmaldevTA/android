package com.amal.dagger.dagger;

import com.amal.dagger.dagger.modules.ApiModule;
import com.amal.dagger.dagger.modules.PicassoModule;
import com.amal.dagger.retrofit.APIComponent;
import com.squareup.picasso.Picasso;


import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {ApiModule.class, PicassoModule.class})
public interface ApplicationComponent {
    APIComponent getApiComponent();
    Picasso getPicasso();

}
