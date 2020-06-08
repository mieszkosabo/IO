package com.example.ioapka;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MultipleScan extends AppCompatActivity {
    private static final String TAG = "MultipleScan";

    private Button newBasketButton;
    private ImageButton backToMainButton;
    private List<String> scannedBarCodes = new ArrayList<String>();
    private Scanner scanner;
    private ListView listView;
    private ArrayList<String> productNames = new ArrayList<String>();
    private ServerCommunication serverCommunication;
    private ArrayAdapter<String> arrayAdapter;
    private final Callback myCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                final String myResponse = response.body().string();

                MultipleScan.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Product product = new Product(myResponse);
                            productNames.add(product.getProductName());
                        } catch (JSONException e) {
                            productNames.add("ERROR");
                        }
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_scan);

        newBasketButton = findViewById(R.id.newBasketButton);
        backToMainButton = findViewById(R.id.backToMainButton);
        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                productNames);
        listView.setAdapter(arrayAdapter);
        serverCommunication = new ServerCommunication();

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
        if (result != null && resultCode == RESULT_OK) {
            String content = result.getContents();
            if (content != null) {
                serverCommunication.lookupEAN(content, myCallback);
                scannedBarCodes.add(content);
                scanner.start();

            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            arrayAdapter.notifyDataSetChanged();
        }
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
