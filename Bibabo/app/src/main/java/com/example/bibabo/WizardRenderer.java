package com.example.bibabo;

import android.graphics.SurfaceTexture;
import android.opengl.GLES30;
import android.opengl.GLES31;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class WizardRenderer implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    protected FloatBuffer mVertexBuffer;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        prepareRes();
        GLES30.glClearColor(0.3f,1.0f,0.8f,1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {

    }

    protected void prepareRes() {

        WizardScene01 scene = new WizardScene01();
        scene.prepareShader();



//        GLUtils.readRawTextFile();
//        GLUtils.loadProgram();

//        AssetUtil a;

    }
}
