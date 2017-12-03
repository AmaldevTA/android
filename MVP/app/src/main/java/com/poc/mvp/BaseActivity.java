package com.poc.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.poc.mvp.service.APIModule;
import com.poc.mvp.dependency.DaggerDependency;
import com.poc.mvp.dependency.Dependency;


public class BaseActivity extends AppCompatActivity {
    Dependency dependency;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dependency = DaggerDependency.builder().aPIModule(new APIModule()).build();
    }

    public Dependency getDependency() {
        return dependency;
    }
}
