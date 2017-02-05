package com.amal.dagger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amal.dagger.dagger.components.ApplicationComponent;
import com.amal.dagger.sorting.Sort;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    int[] array = {7,2,5,1,8,3,4};

    @Inject
    Sort sortMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getApplicationComponent().inject(this);

        int[] sortedArray = sortMethod.sortArray(array);

        System.out.println("Sorted Array - "+ sortedArray.length );
        for (int i : sortedArray){

            System.out.println(i + ", ");
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return ((MyApplication) getApplication()).getApplicationComponent();
    }
}
