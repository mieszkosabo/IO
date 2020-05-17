package com.example.ioapka;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipe extends AppCompatActivity {

    private Button Next;
    private Button AddPhoto;
    private Button MakePhoto;
    private Button Prev;
    private Integer counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_recipes);
        counter = 0;

        AddPhoto = findViewById(R.id.button2);
        MakePhoto = findViewById(R.id.MakePhoto);
        Next = findViewById(R.id.Next);
        Prev = findViewById(R.id.Prev);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnNext();
            }
        });
    }

    public void OnNext() {
        if (counter == 0)  {
            Prev.setVisibility(View.VISIBLE);
            AddPhoto.setVisibility(View.INVISIBLE);
            MakePhoto.setVisibility((View.INVISIBLE));
        }

    }
}
