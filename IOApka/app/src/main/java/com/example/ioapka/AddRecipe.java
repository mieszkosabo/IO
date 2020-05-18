package com.example.ioapka;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

            Callback callback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String myResponse = response.body().string();

                        AddRecipe.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Prev.setText(myResponse);
                            }
                        });
                    }
                }
            };
            ServerCommunication sc = new ServerCommunication();
            sc.lookupCategory(callback);
        }

    }
}
