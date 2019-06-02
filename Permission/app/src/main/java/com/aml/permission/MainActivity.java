package com.aml.permission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static int LOCATION_PERMISSION_CODE = 34;
    private int SETTINGS_PERMISSION_CODE = 33;

    final static int REQUEST_LOCATION = 199;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button locationButton = findViewById(R.id.locationButton);

        if(!isLocationAccessAllowed())
            requestLocationPermission();

        locationButton.setOnClickListener(v -> {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
                Toast.makeText(this,"Gps already enabled",Toast.LENGTH_SHORT).show();
                return;
            }

            if(!hasGPSDevice(this)){
                Toast.makeText(this, "Gps not Supported", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(this,"Gps not enabled",Toast.LENGTH_SHORT).show();
                enableLocation();
            }else{
                Toast.makeText(this,"Gps already enabled",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_PERMISSION_CODE){
            if (!isLocationAccessAllowed()) {
                showExplanation(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }else if (requestCode == REQUEST_LOCATION){
            switch (resultCode) {
                case Activity.RESULT_OK:
                    break;
                case Activity.RESULT_CANCELED:
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0) {
                if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    String permission = permissions[0];
                    showExplanation(permission);
                }
            }
        }
    }

    private boolean isLocationAccessAllowed() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestLocationPermission(){
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showExplanation(String permission){
        boolean showRationale = shouldShowRequestPermissionRationale(permission);
        if (showRationale){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("To continue, let your device turn on location permission.");
            builder.setPositiveButton("Ok", (dialogInterface, i) -> requestLocationPermission());
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("To continue, let your device turn on location permission.");
            builder.setPositiveButton("Ok", (dialogInterface, i) -> openSettingsPage());
            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    private void openSettingsPage(){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, SETTINGS_PERMISSION_CODE);
    }
    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }



    private void enableLocation(){
        LocationRequest locationRequest = LocationRequest.create();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> mTask = LocationServices
                .getSettingsClient(this).checkLocationSettings(builder.build());
        mTask.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                // All location settings are satisfied. The client can initialize location
                // requests here.

            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(MainActivity.this, REQUEST_LOCATION);
                            break;
                        } catch (Exception e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.

                        break;
                }
            }});
    }

}
