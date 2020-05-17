package com.example.ioapka;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;

public class Scanner {
    private IntentIntegrator integrator;

    public Scanner(Activity activity) {
        integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
    }

    public void start() {
        integrator.initiateScan();
    }
}
