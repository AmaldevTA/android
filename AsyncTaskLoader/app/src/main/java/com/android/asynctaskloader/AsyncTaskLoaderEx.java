package com.android.asynctaskloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by amaldev on 21/6/16.
 */
public  class AsyncTaskLoaderEx<Bitmap> extends AsyncTaskLoader<android.graphics.Bitmap> {

    private String url;
    private  android.graphics.Bitmap data;


    public AsyncTaskLoaderEx(final Context context, final String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        if(data != null){
            deliverResult(data);
        }else{
            forceLoad();
        }

    }

    @Override
    public void deliverResult(final android.graphics.Bitmap data) {
        super.deliverResult(data);

    }

    @Override
    public android.graphics.Bitmap loadInBackground() {
        //TODO load and return bitmap
        try {

            URL _url = new URL(url);
            URLConnection conn = _url.openConnection();
            data = BitmapFactory.decodeStream(conn.getInputStream());
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
