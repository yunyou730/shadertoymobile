package com.example.bibabo;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.bibabo.utils.CameraUtil;

import java.util.Date;

public class WizardRenderer implements GLSurfaceView.Renderer {

    private CameraUtil mCameraManager = new CameraUtil();

    private Triangle mTriangle;
    private Rectangle mRect;
    private CameraDrawer mCamDrawer;

    private Date mBeginTime;

    WizardView mHolderView = null;
    void setHolderView(WizardView holderView)
    {
        mHolderView = holderView;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glClearColor(0.3f,1.0f,0.8f,1.0f);

        mTriangle = new Triangle();
        mRect = new Rectangle();
        mCamDrawer = new CameraDrawer();

        mCameraManager.openCamera();
        mCamDrawer.getSurfaceTexture().setOnFrameAvailableListener(mHolderView);
        mCameraManager.setPreviewTexture(mCamDrawer.getSurfaceTexture());

        mBeginTime = new Date();
        System.currentTimeMillis();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        Date now = new Date();
//        now - mBeginTime;
        long ellapsedMS = (System.currentTimeMillis() - mBeginTime.getTime());
        float ellapsedSecs = ellapsedMS / 1000.f;
//        Log.d("ayy", String.valueOf(ellapsedSecs));

        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        mRect.draw();
        mTriangle.draw();

        mCamDrawer.getSurfaceTexture().updateTexImage();
        mCamDrawer.draw(mCameraManager.getNeedRotDegree(),ellapsedSecs);

        WizardApp.getInstance().getEventDispatcher().SendToListeners();
        WizardApp.getInstance().getEventDispatcher().ClearAllEvents();
    }

    public void ChangeCameraDrawerShaderProgram(int program)
    {
        mBeginTime = new Date();
        mCamDrawer.replaceShaderProgram(program);
    }


}
