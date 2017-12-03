package com.poc.mvp.dependency;

import com.poc.mvp.service.APIModule;
import com.poc.mvp.home.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {APIModule.class})
public interface Dependency {
    void inject(MainActivity mainActivity);
}