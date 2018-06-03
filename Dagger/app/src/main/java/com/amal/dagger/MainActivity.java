package com.amal.dagger;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.amal.dagger.dagger.modules.ControllerModule;
import com.amal.dagger.sorting.Sort;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;

    int[] array = {7,2,5,1,8,3,4};

    @Inject
    Context context;

    @Inject
    Sort sortMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ((MyApplication) getApplication()).getApplicationComponent()
                .newControllerComponent(new ControllerModule())
                .inject(this);

        Log.d("action", "button click -" + context.getPackageName());
        Log.d("action", "button click -" + sortMethod.getClass());
        int[] sortedArray = sortMethod.sortArray(array);
    }


}
