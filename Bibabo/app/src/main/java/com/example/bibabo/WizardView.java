package com.example.bibabo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.TextureView;

import androidx.annotation.NonNull;

public class WizardView
        extends GLSurfaceView
        implements SurfaceTexture.OnFrameAvailableListener {

    private final WizardRenderer renderer;

    public WizardView(Context context) {
        super(context);
        setEGLContextClientVersion(3);
        renderer = new WizardRenderer();
        setRenderer(renderer);
        renderer.setHolderView(this);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }
}
