package com.example.bibabo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bibabo.utils.ImageFileConvert;

public class LauncherActivity extends Activity {

    boolean mIsAllPermissionsCheckOK = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launcher);

        WizardApp.createInstance();
        if(checkAndRequestAllPermissions())
        {
            onAllPermissionsOK();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    boolean checkCameraPermission()
    {
        int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    boolean checkStoragePermission()
    {
        int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    boolean checkInternetPermission()
    {
        int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    boolean checkAndRequestCameraPermission() {
        if(!checkCameraPermission())
        {
            String[] pers = new String[1];
            pers[0] = Manifest.permission.CAMERA;
            ActivityCompat.requestPermissions(this, pers,1);
            return false;
        }
        return true;
    }

    boolean checkAndRequestStoragePermission() {
        if(!checkStoragePermission())
        {
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            };
            ActivityCompat.requestPermissions(this, permissions,1);
            return false;
        }
        return true;
    }

    boolean checkAndRequestInternetPermission() {
        if(!checkInternetPermission()) {
            String[] permissions = {
                Manifest.permission.INTERNET
            };
            ActivityCompat.requestPermissions(this,permissions,1);
        }
        return true;
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults)
    {
        if(!mIsAllPermissionsCheckOK)
        {
            if(checkAndRequestAllPermissions())
            {
                onAllPermissionsOK();
            }
        }
    }

    protected boolean checkAndRequestAllPermissions()
    {
        return checkAndRequestCameraPermission() &&
                checkAndRequestStoragePermission() &&
                checkAndRequestInternetPermission();
    }

    void onAllPermissionsOK()
    {
        mIsAllPermissionsCheckOK = true;
        showCameraActivity();
    }

    void showCameraActivity()
    {
        Intent intent = new Intent();
        intent.setClass(this,WizardActivity.class);
        this.startActivity(intent);
        this.finish();
    }

}
