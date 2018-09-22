package com.amal.dagger.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface APIComponent {
    @Headers("Content-Type: application/json")
    @GET("payment/{id}/profile")
    Call<String> getProfile(@Path("id") int id, @Header("Country-Code") String code);
}
