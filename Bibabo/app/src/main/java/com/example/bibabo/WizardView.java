package com.example.bibabo;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class WizardView extends GLSurfaceView {

    private final WizardRenderer renderer;

    public WizardView(Context context) {
        super(context);
        setEGLContextClientVersion(3);
        renderer = new WizardRenderer();
        setRenderer(renderer);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
