package com.example.bibabo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class WizardActivity extends Activity {

    WizardView view = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new WizardView(this);
        setContentView(view);
    }

}
