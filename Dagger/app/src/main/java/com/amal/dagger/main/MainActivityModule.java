package com.amal.dagger.main;

import android.content.Context;

import com.amal.dagger.dagger.modules.ActivityContextModule;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = ActivityContextModule.class)
public class MainActivityModule {

    @Provides
    public Adapter mainAdapter(@Named("activity_context")Context context){
        return new Adapter(context);
    }
}
