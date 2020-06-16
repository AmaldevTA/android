package com.aml.soap;

import android.app.Application;

import javax.net.ssl.HttpsURLConnection;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(new TLSSocketFactory(getApplicationContext()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
