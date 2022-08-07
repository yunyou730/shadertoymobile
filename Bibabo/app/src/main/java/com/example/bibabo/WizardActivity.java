package com.example.bibabo;

import static android.app.AlertDialog.*;

import android.Manifest;
//import android.annotation.NonNull;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import androidx.camera.core.CameraX;
//import androidx.camera.core.ImageCapture;

public class WizardActivity extends Activity {

    WizardView view = null;

    Camera mCamera = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("ayy","WizardActivity,0");

        int permissionState = PackageManager.PERMISSION_DENIED;
//        permissionState = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
//        if(permissionState == PackageManager.PERMISSION_DENIED)
//        {
//
//            String[] pers = new String[1];
//            pers[0] = Manifest.permission.READ_EXTERNAL_STORAGE;
//            ActivityCompat.requestPermissions(this, pers,1);
//            return;
//        }
//        permissionState = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if(permissionState == PackageManager.PERMISSION_DENIED)
//        {
//
//            String[] pers = new String[1];
//            pers[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
//            ActivityCompat.requestPermissions(this, pers,1);
//            return;
//        }


        permissionState = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissionState == PackageManager.PERMISSION_DENIED)
        {
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            };
            ActivityCompat.requestPermissions(this, permissions,1);
            return;
        }



        // Check camera permission
        permissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
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

        showStoragePathInfo();
    }


    protected void showWizardView()
    {
        view = new WizardView(this);
        setContentView(view);
        view.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    showStoragePathInfo();
                }
//                alertMessage(event.toString());
                return true;
            }
        });
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

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void showStoragePathInfo() {

        String rootDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
//        File pathFile = new File();

        File f = new File(rootDir,"test.fsh");
        if(f.exists())
        {
//            alertMessage(f.getAbsolutePath());
            Log.d("ayy","exist!");
        }
        else
        {
            alertMessage(rootDir);
        }

        try
        {

            FileInputStream fis = new FileInputStream(f);

            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len = 0;
            while((len = fis.read(temp)) > 0)
            {
                sb.append(new String(temp,0,len));
            }
            fis.close();
            Log.d("ayy",sb.toString());

            alertMessage(sb.toString());

        }
        catch(Exception ex)
        {
            Log.d("ayy",ex.toString());
            alertMessage(ex.toString());
        }


    }


    protected void alertMessage(String msg)
    {
        Builder b = new AlertDialog.Builder(this);
        b.setMessage(msg).setCancelable(true).show();
    }
}
