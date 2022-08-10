package com.example.bibabo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class WizardView
        extends GLSurfaceView
        implements SurfaceTexture.OnFrameAvailableListener {

    private WizardRenderer mRenderer = null;

    public WizardView(Context context) {
        super(context);
        doInit();
    }

    public WizardView(Context context, AttributeSet set)
    {
        super(context,set);
        doInit();
    }

    private void doInit(){

        setEGLContextClientVersion(3);
        mRenderer = new WizardRenderer();
        setRenderer(mRenderer);
        mRenderer.setHolderView(this);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }
}
