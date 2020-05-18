package com.example.ioapka;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
                        String myResponse = response.body().string();
                        myResponse = myResponse.substring(1, myResponse.length() - 1);
                        final ArrayList<String> categories= new ArrayList<String>(Arrays.asList(myResponse.split(",")));
                        final TextView text = findViewById(R.id.textView);
                        AddRecipe.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                text.setText(categories.get(0).substring(1, categories.get(0).length() - 1));
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
