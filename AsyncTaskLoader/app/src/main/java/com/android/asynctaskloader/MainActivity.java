package com.android.asynctaskloader;

import android.graphics.Bitmap;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap>{

    int TASK_ID = 1024;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportLoaderManager().initLoader(TASK_ID, null, this);

    }

    @Override
    public Loader<Bitmap> onCreateLoader(final int id, final Bundle args) {
        System.out.println("onCreateLoader");
        return new AsyncTaskLoaderEx(MainActivity.this, "http://www.gettyimages.ca/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg");
    }

    @Override
    public void onLoadFinished(final Loader<Bitmap> loader, final Bitmap result) {

        if (result == null){
            System.out.println("result is null");
        }else{
            System.out.println("result is not null - "+result.getHeight());
        }
        //TODO use result
    }

    @Override
    public void onLoaderReset(final Loader<Bitmap> loader) {
    }

}



