package com.example.bibabo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

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

    public WizardView(Context context, AttributeSet set)
    {
        super(context,set);

        setEGLContextClientVersion(3);
        renderer = new WizardRenderer();
        setRenderer(renderer);
        renderer.setHolderView(this);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }
}
