package com.example.ioapka;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.IOException;

public class SingleScan extends AppCompatActivity {
    private TextView productNameTextView;
    private TextView nutritionalValuesTextView;
    private ImageButton backToMainButton;
    private final Callback myCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                final String myResponse = response.body().string();

                SingleScan.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Product product = new Product(myResponse);
                            productNameTextView.setText(product.getProductName());
                            nutritionalValuesTextView.setText(
                                    product.getNutritionalValues(), TextView.BufferType.SPANNABLE);
                        } catch (JSONException e) {
                            productNameTextView.setText("ERROR");
                        }
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_scan);

        productNameTextView = findViewById(R.id.productNameTextView);
        nutritionalValuesTextView = findViewById(R.id.nutritionalValuesTextView);
        backToMainButton = findViewById(R.id.backToMainButton);

        backToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        Scanner scanner = new Scanner(this);
        scanner.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            String content = result.getContents();
            if (content != null) {
                productNameTextView.setText(result.getContents());
                ServerCommunication serverCommunication = new ServerCommunication();
                serverCommunication.lookupEAN(content, myCallback);
            } else {
                super.onActivityResult(requestCode, resultCode, data);
                openMainActivity();
            }
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
