package com.amal.dagger.dagger.modules;

import android.content.Context;

import com.amal.dagger.sorting.QuickSort;
import com.amal.dagger.sorting.Sort;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;


@Module
public class ControllerModule {
    @Provides
    @Inject
    Sort getSortingMethod(Context context){
        return new QuickSort();
    }
}
