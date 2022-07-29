package com.example.bibabo;

import android.app.Activity;
import android.graphics.Camera;
import android.os.Bundle;

import androidx.annotation.Nullable;
//import androidx.camera.core.CameraX;
//import androidx.camera.core.ImageCapture;

public class WizardActivity extends Activity {

    WizardView view = null;

    Camera mCamera = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new WizardView(this);
        setContentView(view);



//        mCamera = Camera.open(1);
    }
}
