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

public class WizardActivity extends Activity {
    WizardView view = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showStoragePathInfo();
        showWizardView();
    }

    protected void showWizardView()
    {
//        view = new WizardView(this);
//        setContentView(view);
        setContentView(R.layout.wizard);
        view = findViewById(R.id.wizardView2);

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

    protected void showStoragePathInfo() {
        String rootDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();

        File f = new File(rootDir,"test.fsh");
        if(f.exists())
        {
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
