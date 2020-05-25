package com.example.ioapka;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.LinkedList;
import java.util.List;

public class MultipleScan extends AppCompatActivity {
    private static final String TAG = "MultipleScan";

    private Button newBasketButton;
    private ImageButton backToMainButton;
    private List<String> scannedBarCodes = new LinkedList<String>();
    private Scanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_scan);

        newBasketButton = findViewById(R.id.newBasketButton);
        backToMainButton = findViewById(R.id.backToMainButton);

        scanner = new Scanner(this);

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        newBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanner.start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String content = result.getContents();
            if (content != null) {
                scannedBarCodes.add(content);
                scanner.start();
                Log.i(TAG, scannedBarCodes.toString());
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
