package com.example.bibabo;

import android.app.AlertDialog;
import android.util.Log;

public class WizardApp {
    public static WizardApp sInstance = null;

    protected WizardActivity mWizardActivity = null;

    protected  WizardApp() {
        super();
    }

    public static WizardApp createInstance()
    {
        sInstance = new WizardApp();
        return sInstance;
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
}
