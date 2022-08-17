package com.example.bibabo;

import android.app.AlertDialog;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import com.example.bibabo.event.EventDispatcher;
import com.example.bibabo.server.WizardServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WizardApp {
    public static WizardApp sInstance = null;

    protected WizardActivity mWizardActivity = null;
    protected WizardServer mServer = null;
    protected EventDispatcher mEventDispatcher = null;

    protected final int mPort = 7324;

    public static WizardApp createInstance()
    {
        sInstance = new WizardApp();
        return sInstance;
    }

    protected  WizardApp() {
        super();
        mEventDispatcher = new EventDispatcher();
    }

    public static void cleanUp()
    {
        sInstance = null;
    }

    public static WizardApp getInstance(){
        return sInstance;
    }

    public void registerActivity(WizardActivity activity) {
        mWizardActivity = activity;
    }

    public void unregisterActivity(){
        mWizardActivity = null;
    }

    public void showPopupMessage(String message) {
        if(mWizardActivity != null)
        {
            AlertDialog.Builder b = new AlertDialog.Builder(mWizardActivity);
            b.setMessage(message).setCancelable(true).show();
        }
        else
        {
            Log.e("ayy",message + " | mWizardActivity == null");
        }
    }

    public void startServer()
    {
        InetSocketAddress host = new InetSocketAddress(getLocalIPAddress(mWizardActivity),getPort());
        mServer = new WizardServer(host);
        mServer.start();
    }

    public void stopServer()
    {
        try {
            mServer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getLocalIPAddress (Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ipAddress = formatIP(wifiInfo.getIpAddress());
        return ipAddress;
    }

    public String formatIP (int ip) {
        return Formatter.formatIpAddress(ip);
    }

    public int getPort()
    {
        return mPort;
    }


    public EventDispatcher getEventDispatcher()
    {
        return mEventDispatcher;
    }

    public void onUpdate()
    {

    }

}
