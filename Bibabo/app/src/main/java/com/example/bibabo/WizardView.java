package com.example.bibabo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

import com.example.bibabo.event.EventDispatcher;
import com.example.bibabo.event.EventListener;
import com.example.bibabo.utils.ShaderUtil;

public class WizardView
        extends GLSurfaceView
        implements SurfaceTexture.OnFrameAvailableListener,EventListener
{

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

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        super.surfaceCreated(holder);
        WizardApp.getInstance().getEventDispatcher().RegisterEvent(EventDispatcher.EventType.ReceiveShaderCode,this);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        super.surfaceDestroyed(holder);
    }

    @Override
    public void onEvent(EventDispatcher.EventType eventType,EventDispatcher.WizardEvent event) {
        if(eventType == EventDispatcher.EventType.ReceiveShaderCode)
        {
            // @miao @todo
            // compile shader
            EventDispatcher.ShaderCodeChangeEvent evt = (EventDispatcher.ShaderCodeChangeEvent)event;
            Log.d("ayy",evt.mVertCode);
            Log.d("ayy",evt.mFragCode);

            int newProgram = ShaderUtil.createProgram(evt.mVertCode,evt.mFragCode);
            mRenderer.ChangeCameraDrawerShaderProgram(newProgram);
        }

    }
}
