package com.amal.dagger.dagger.modules;

import android.app.Application;
import android.content.Context;


import com.amal.dagger.sorting.MergeSort;
import com.amal.dagger.sorting.QuickSort;
import com.amal.dagger.sorting.Sort;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }

    @Provides
    Sort getSortingMethod(){
        return new QuickSort();
    }


}
