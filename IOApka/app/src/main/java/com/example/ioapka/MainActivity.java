package com.example.ioapka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button fridgeButton = findViewById(R.id.fridgeButton);
        // wygląda na to, że takie coś sprawia, że kliknięcia idą do tego onClick niżej
        fridgeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // każdy button może być tutaj w odzielnym case'ie i pyk
        switch (v.getId()) {
            case R.id.fridgeButton:
                Toast.makeText(this, "Button clicked",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
