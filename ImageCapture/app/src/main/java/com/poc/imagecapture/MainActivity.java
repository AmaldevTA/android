package com.poc.imagecapture;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "MainActivity";
    public static int CAMERA_REQUEST_LOW_RESOLUTION = 101;
    public static int CAMERA_REQUEST_HIGH_RESOLUTION_INTERNAL = 102;
    public static int CAMERA_REQUEST_HIGH_RESOLUTION_EXTERNAL = 103;
    public static int PIC_CROP= 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cropImageInternal();
    }

    private void cropImageFromExternal(Uri picUri){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(picUri, "image/*");

        // set crop properties here
        cropIntent.putExtra("crop", true);
        // indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // indicate output X and Y
        cropIntent.putExtra("outputX", 128);
        cropIntent.putExtra("outputY", 128);
        // retrieve data on return
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, PIC_CROP);
    }

    private void cropImageInternal(){
        final File imagePath = new File(getFilesDir(), "images");
        final File imageFile = new File(imagePath, "new_image.jpg");

        final Uri providedUri = FileProvider.getUriForFile(
                MainActivity.this, "com.poc.imagecapture.fileprovider", imageFile);

        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(providedUri, "image/*");
        cropIntent.putExtra("crop", "true");
        cropIntent.putExtra("aspectX", 9);
        cropIntent.putExtra("aspectY", 16);
        cropIntent.putExtra("return-data", true);
        cropIntent.putExtra("scale", true);

        // Exception will be thrown if read permission isn't granted
        cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(cropIntent, PIC_CROP);
    }

    private void highResolutionInternal(){
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, MyFileContentProvider.CONTENT_URI);
        startActivityForResult(i, CAMERA_REQUEST_HIGH_RESOLUTION_INTERNAL);
    }

    private void highResolutionExternal(){
        //String filename = Environment.getExternalStorageDirectory().getPath() + "/images/new_image.jpg";
        //Uri imageUri = Uri.fromFile(new File(filename));

        File parentFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File newFile = new File(parentFile, "new_image.jpg");

        Uri imageUri = Uri.fromFile(newFile);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult (cameraIntent, CAMERA_REQUEST_HIGH_RESOLUTION_EXTERNAL);
    }

    private void lowResolution(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_LOW_RESOLUTION);
    }

    public static void addImageToGallery(final String filePath, final Context context) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_LOW_RESOLUTION) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Log.d(TAG, "ImageSize - " + photo.getHeight());
        }

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_HIGH_RESOLUTION_INTERNAL) {
            File out = new File(getFilesDir(), "newImage.jpg");
            if(!out.exists()) {
                Log.d(TAG, "File not Found");
                return;
            }
            Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
            Log.d(TAG, "ImageSize - " + mBitmap.getHeight());

        }

        if(resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_HIGH_RESOLUTION_EXTERNAL){
            File out = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() , "new_image.jpg");
            if(!out.exists()) {
                Log.d(TAG, "File not Found");
                return;
            }
            Bitmap mBitmap = BitmapFactory.decodeFile(out.getAbsolutePath());
            Log.d(TAG, "ImageSize - " + mBitmap.getHeight());
            addImageToGallery(out.getPath(), this);
        }

        if (requestCode == PIC_CROP) {
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data");
                Log.d(TAG, "ImageSize - " + selectedBitmap.getHeight());

            }
        }
    }
}
