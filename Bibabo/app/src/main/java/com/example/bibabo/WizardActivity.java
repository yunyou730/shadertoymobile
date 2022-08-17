package com.example.bibabo;

//import android.annotation.NonNull;
import android.app.Activity;
        import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.bibabo.event.EventDispatcher;
import com.example.bibabo.event.EventListener;
import com.example.bibabo.server.WizardServer;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class WizardActivity extends Activity implements EventListener {
    WizardView mView = null;

    ArrayList<String> mShaderError = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WizardApp.getInstance().registerActivity(this);
        WizardApp.getInstance().getEventDispatcher().RegisterEvent(EventDispatcher.EventType.ShaderCodeError,this);

        launchServer();
//        showStoragePathInfo();
        showWizardView();

        refreshUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WizardApp.getInstance().unregisterActivity();
        WizardApp.getInstance().stopServer();
    }

    protected void showWizardView()
    {
        setContentView(R.layout.wizard);
        mView = findViewById(R.id.wizardView2);

        mView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
//                    showStoragePathInfo();
                }
                return true;
            }
        });
    }

    protected void showStoragePathInfo() {
        String rootDir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();

        File f = new File(rootDir,"test.fsh");
        if(f.exists())
        {
            Log.d("ayy","exist!");
        }
        else
        {
            WizardApp.getInstance().showPopupMessage("rootDir:" + rootDir);
        }

        try
        {
            FileInputStream fis = new FileInputStream(f);

            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len = 0;
            while((len = fis.read(temp)) > 0)
            {
                sb.append(new String(temp,0,len));
            }
            fis.close();
            WizardApp.getInstance().showPopupMessage(sb.toString());

        }
        catch(Exception ex)
        {
            WizardApp.getInstance().showPopupMessage(ex.toString());
        }
    }

    protected void launchServer()
    {
        WizardApp.getInstance().startServer();
    }

    protected void refreshUI()
    {
        String ip = WizardApp.getInstance().getLocalIPAddress(this);
        TextView text = findViewById(R.id.textView);
        text.setTextColor(0xFFFF00FF);
        text.setText("ws://" + ip + ":" + WizardApp.getInstance().getPort());
    }

    @Override
    public void onEvent(EventDispatcher.EventType eventType, EventDispatcher.WizardEvent event) {
        if(eventType == EventDispatcher.EventType.ShaderCodeError)
        {
            EventDispatcher.ShaderCodeErrorEvent evt = (EventDispatcher.ShaderCodeErrorEvent)event;
            WizardApp.getInstance().showPopupMessage(evt.mError);
        }
    }
}
