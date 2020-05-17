package com.example.ioapka;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult = findViewById(R.id.resTest);

        Button fridgeButton = findViewById(R.id.fridgeButton);
        // wygląda na to, że takie coś sprawia, że kliknięcia idą do tego onClick niżej
        fridgeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // każdy button może być tutaj w odzielnym case'ie i pyk
        switch (v.getId()) {
            case R.id.fridgeButton:
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
                break;
        }
    }
}
