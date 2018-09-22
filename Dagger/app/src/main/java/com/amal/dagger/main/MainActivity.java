package com.amal.dagger.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amal.dagger.MyApplication;
import com.amal.dagger.R;
import com.amal.dagger.dagger.ApplicationComponent;
import com.amal.dagger.dagger.modules.ActivityContextModule;
import com.amal.dagger.retrofit.APIComponent;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button;

    @Inject
    Adapter adapter;

    APIComponent apiComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyApplication myApplication = (MyApplication) getApplication();
        ApplicationComponent component = myApplication.getApplicationComponent();


        apiComponent = component.getApiComponent();

        MainActivityComponent activityComponent =  DaggerMainActivityComponent.builder()
                .activityContextModule(new ActivityContextModule(this))
                .applicationComponent(component)
                .build();

        apiComponent = activityComponent.getApiComponent();

        activityComponent.injectMainActivity(this);


        button = findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(adapter != null)
            Toast.makeText(this, "non null " + adapter.getClass(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
    }


}
