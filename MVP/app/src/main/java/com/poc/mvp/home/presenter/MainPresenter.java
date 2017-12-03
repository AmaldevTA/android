package com.poc.mvp.home.presenter;

import com.poc.mvp.service.Service;
import com.poc.mvp.home.ViewCallBacks;
import com.poc.mvp.model.Conversion;

import java.util.Map;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class MainPresenter implements Service.CurrentRateCallback{
    private final Service service;
    private final ViewCallBacks view;
    private CompositeSubscription subscriptions;

    public MainPresenter(Service service, ViewCallBacks view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getRate(Map<String, String> options){
        view.showProgress();
        Subscription subscription = service.getRate(this, options);
        subscriptions.add(subscription);
    }

    @Override
    public void onSuccess(Conversion response) {
        view.removeProgress();
        view.getRateSuccess(response);
    }

    @Override
    public void onError(Throwable networkError) {
        view.removeProgress();
        view.onFailure(networkError);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
