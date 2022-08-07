package com.example.bibabo;


import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import java.io.IOException;

public class CameraUtil {
    private Camera camera = null;

    public void openCamera()
    {
        camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(parameters);

//        camera.setDisplayOrientation(180);
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

//
//    public void getCameraInfo()
//    {
//        Camera.CameraInfo info = new Camera.CameraInfo();
//        Camera.getCameraInfo(camera,info);
//    }

}
