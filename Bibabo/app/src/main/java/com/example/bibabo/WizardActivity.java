package com.example.bibabo;

import android.Manifest;
//import android.annotation.NonNull;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

        Log.d("ayy","WizardActivity,0");

        // Check camera permission
        int permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(permissionState == PackageManager.PERMISSION_GRANTED)
        {
            Log.d("ayy","WizardActivity,1");
            showWizardView();
        }
        else
        {
            Log.d("ayy","WizardActivity,2");
            // Request camera permission
            String[] pers = new String[1];
            pers[0] = Manifest.permission.CAMERA;
            ActivityCompat.requestPermissions(this, pers,1);
        }
    }

    protected void showWizardView()
    {
        view = new WizardView(this);
        setContentView(view);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("------\n");
        for(int i = 0;i < permissions.length;i++)
        {
            sb.append(permissions[i] + "=>" + grantResults[i] + "\n");
        }
        sb.append("======\n");
        Log.d("ayy",sb.toString());


        boolean bCameraPermissionGranted = false;
        if(permissions.length > 0)
        {
            for(int i = 0;i < permissions.length;i++)
            {
                if(permissions[i].equals("android.permission.CAMERA"))
                {
                    if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    {
                        bCameraPermissionGranted = true;
                    }
                    break;
                }
            }
        }

        if(bCameraPermissionGranted)
        {
            showWizardView();
        }
    }
}
