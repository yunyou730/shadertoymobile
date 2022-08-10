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
import android.graphics.Shader;
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
    WizardView mView = null;
    ShaderServer mServer = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WizardApp.getInstance().registerActivity(this);

        showStoragePathInfo();
        showWizardView();
        launchServer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WizardApp.getInstance().unregisterActivity();
    }

    protected void showWizardView()
    {
        setContentView(R.layout.wizard);
        mView = findViewById(R.id.wizardView2);

        mView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    showStoragePathInfo();
                }
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
            WizardApp.getInstance().showPopupMessage("rootDir:" + rootDir);
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
            WizardApp.getInstance().showPopupMessage(sb.toString());

        }
        catch(Exception ex)
        {
            WizardApp.getInstance().showPopupMessage(ex.toString());
        }
    }

    protected void launchServer()
    {
        mServer = new ShaderServer();
    }
}
