package com.poc.mvp.service;

import com.poc.mvp.model.Conversion;

import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Service {

    private final APIService apiService;

    public Service(APIService apiService) {
        this.apiService = apiService;
    }

    public Subscription getRate(final CurrentRateCallback callback, Map<String, String> options) {

        return apiService.getCurrentRate(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Conversion>() {
                    @Override
                    public void onCompleted() {
                        System.out.println("<<<>>>  onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("<<<>>>  onError");
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Conversion response) {
                        System.out.println("<<<>>>  onNext " + response.getUSDINR());
                        callback.onSuccess(response);
                    }
                });
    }

    public interface CurrentRateCallback{
        void onSuccess(Conversion response);

        void onError(Throwable networkError);
    }
}
