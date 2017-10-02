package com.poc.imagecapture;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * Created by amal on 1/10/17.
 */

public class MyFileContentProvider extends ContentProvider {

    public static final Uri CONTENT_URI = Uri.parse("content://com.poc.imagecapture/");

    private static final HashMap<String, String> MIME_TYPES = new HashMap<>();

    static {
        MIME_TYPES.put(".jpg", "image/jpeg");
        MIME_TYPES.put(".jpeg", "image/jpeg");
    }

    @Override
    public boolean onCreate() {

        try {
            File mFileParent = new File(getContext().getFilesDir(), "images");
            mFileParent.mkdir();
            File mFile = new File(mFileParent, "new_image.jpg");
            if(!mFile.exists()) {
                mFile.createNewFile();
            }

            getContext().getContentResolver().notifyChange(CONTENT_URI, null);
            return (true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getType(Uri uri) {

        String path = uri.toString();
        for (String extension : MIME_TYPES.keySet()) {
            if (path.endsWith(extension)) {
                return (MIME_TYPES.get(extension));
            }
        }
        return (null);
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {

        File f = new File(getContext().getFilesDir(), "images/new_image.jpg");
        if (f.exists()) {
            return (ParcelFileDescriptor.open(f, ParcelFileDescriptor.MODE_READ_WRITE));
        }
        throw new FileNotFoundException(uri.getPath());
    }

    @Override
    public Cursor query(Uri url, String[] projection, String selection,
                        String[] selectionArgs, String sort) {
        throw new RuntimeException("Operation not supported");
    }

    @Override

    public Uri insert(Uri uri, ContentValues initialValues) {
        throw new RuntimeException("Operation not supported");
    }

    @Override

    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        throw new RuntimeException("Operation not supported");
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        throw new RuntimeException("Operation not supported");
    }

}
