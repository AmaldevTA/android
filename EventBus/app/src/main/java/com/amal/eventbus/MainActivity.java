package com.amal.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HelloWorldEvent event){
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void onEvent(AnotherEvent event){
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public void sendEvent(View v){
        EventBus.getDefault().post(new AnotherEvent("Hello EventBus!"));
    }
}
