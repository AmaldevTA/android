package com.poc.mvp.service;

import com.poc.mvp.model.Conversion;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;


public interface APIService {

    @GET("convert?")
    Observable<Conversion> getCurrentRate(@QueryMap Map<String, String> options);
}
