package com.poc.mvp.home;

import android.os.Bundle;
import com.poc.mvp.BaseActivity;
import com.poc.mvp.R;
import com.poc.mvp.service.Service;
import com.poc.mvp.home.presenter.MainPresenter;
import com.poc.mvp.model.Conversion;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements ViewCallBacks{

    @Inject
    public Service service;
    private MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDependency().inject(this);
        presenter = new MainPresenter(service, this);

        Map<String, String> params = new LinkedHashMap<>();
        params.put("q", "USD_INR");
        params.put("compact", "ultra");

        presenter.getRate(params);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void removeProgress() {

    }

    @Override
    public void onFailure(Throwable appErrorMessage) {

    }

    @Override
    public void getRateSuccess(Conversion conversion) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onStop();
    }
}
