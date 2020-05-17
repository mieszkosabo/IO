package com.example.ioapka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextViewResult; // pole tekstowe do testowania http responsów
    private Button fridgeButton;
    private Button recipesButton;
    private Button scanButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult = findViewById(R.id.resTest);

        fridgeButton = findViewById(R.id.fridgeButton);
        recipesButton = findViewById(R.id.recipesButton);
        scanButon = findViewById(R.id.scanButton);

        fridgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFridge();
            }
        });

        recipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipes();
            }
        });

        scanButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSingleScan();
            }
        });
    }

    public void openSingleScan() {
        Intent intent = new Intent(this, SingleScan.class);
        startActivity(intent);
    }

    public void openRecipes() {
        Intent intent = new Intent(this, RecipesMenu.class);
        startActivity(intent);
    }

    public void openFridge() {
        // Należy napisać swój Callback zawierający onFailure i OnResponse
        // tak jak niżej. W onResponse pisze się co chcemy zrobić z responsem.
        // TODO: fajniej by było jakby serverCommunication returnował JSON'a z odp np
        // TODO: a nie że trzeba pisać callbacka, ale na razie nie wiem jak to zrobić
        Callback myCallback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }
            }
        };
        // Potem tworzymy obiekt ServerCommunication i możemy użyć np metody lookupEAN
        ServerCommunication sc = new ServerCommunication();
        sc.lookupEAN("111111111111", myCallback);
    }

    @Override
    public void onClick(View v) {
        // każdy button może być tutaj w odzielnym case'ie i pyk
        switch (v.getId()) {
            case R.id.fridgeButton:

        }
    }
}