package com.example.bibabo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
//import androidx.camera.core.CameraX;
//import androidx.camera.core.ImageCapture;

public class WizardActivity extends Activity {

    WizardView view = null;

    Camera mCamera = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Request camera permission
        String[] pers = new String[1];
        pers[0] = Manifest.permission.CAMERA;
        ActivityCompat.requestPermissions(this, pers,1);


        // Check camera permission
        int n = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);// == PackageManager.PERMISSION_GRANTED
        if(n == PackageManager.PERMISSION_GRANTED)
        {
            // If OK ,goto next codes
            view = new WizardView(this);
            setContentView(view);
        }



//        mCamera = Camera.open(1);
    }
}
