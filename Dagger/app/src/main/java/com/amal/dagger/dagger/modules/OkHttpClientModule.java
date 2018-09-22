package com.amal.dagger.dagger.modules;

import android.content.Context;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = {AppContextModule.class})
public class OkHttpClientModule {

    @Provides
    public OkHttpClient okHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    public Cache cache(File cacheFile){
        return new Cache(cacheFile, 10 * 1000 * 1000); //10 MB
    }

    @Provides
    public File file(@Named("application_context") Context context){
        File file = new File(context.getCacheDir(), "HttpCache");
        file.mkdirs();
        return file;
    }

    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
