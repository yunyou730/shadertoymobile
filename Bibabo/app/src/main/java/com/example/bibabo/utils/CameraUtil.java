package com.example.bibabo.utils;


import android.graphics.SurfaceTexture;
import android.hardware.Camera;

import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;

import android.util.Log;
import android.view.SurfaceHolder;
//import android.hardware.Camera2;

import java.io.IOException;
import java.util.List;

public class CameraUtil {
    private Camera camera = null;

    public void openCamera()
    {
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(parameters);
        camera.startPreview();
    }

    public void stopCamera()
    {
        if(camera != null)
        {
            camera.stopPreview();
            camera.release();
        }
    }

    public void setPreviewDisplay(SurfaceHolder surfaceHolder)
    {
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPreviewTexture(SurfaceTexture surfaceTexture)
    {
        try {
            camera.setPreviewTexture(surfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
