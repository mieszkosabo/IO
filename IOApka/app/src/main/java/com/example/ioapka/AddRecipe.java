package com.example.ioapka;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.ioapka.R.drawable.button_background2;

public class AddRecipe extends AppCompatActivity {

    private Button Next;
    private Button AddPhoto;
    private Button MakePhoto;
    private Button Prev;
    private Integer counter;
    private ArrayList<String> Tags;
    public ConstraintLayout cl;

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

        cl = findViewById(R.id.MyLayout);
    }

    public void OnNext() {
        if (counter == 0)  {
            Prev.setVisibility(View.VISIBLE);
            AddPhoto.setVisibility(View.INVISIBLE);
            MakePhoto.setVisibility(View.INVISIBLE);

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
                        Tags = new ArrayList<String>(Arrays.asList(myResponse.split(",")));
                        final ArrayList<CheckBox> cbs = new ArrayList<CheckBox>();
                        AddRecipe.this.runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                            @Override
                            public void run() {
                                for (int i = 0; i < Tags.size(); i++) {
                                    if (i == 0) {
                                        ConstraintSet set = new ConstraintSet();
                                        CheckBox cb = new CheckBox(AddRecipe.this);
                                        cb.setId(View.generateViewId());
                                        cl.addView(cb);
                                        set.clone(cl);
                                        set.connect(cb.getId(), ConstraintSet.LEFT, cl.getId(), ConstraintSet.LEFT, 120);
                                        set.connect(cb.getId(), ConstraintSet.TOP, findViewById(R.id.toolbarAdding).getId(), ConstraintSet.BOTTOM, 40);
                                        set.applyTo(cl);
                                        cb.setText(Tags.get(i).substring(1, Tags.get(i).length() - 1));
                                        cb.setBackgroundResource(button_background2);
                                        cb.setTextColor(Color.parseColor("#FFF6CE"));
                                        cb.setPadding(4, 4, 12, 4);
                                        cbs.add(cb);
                                    }
                                    if (i > 0 && i < 10) {
                                        ConstraintSet set = new ConstraintSet();
                                        CheckBox cb = new CheckBox(AddRecipe.this);
                                        cb.setId(View.generateViewId());
                                        cl.addView(cb);
                                        set.clone(cl);
                                        set.connect(cb.getId(), ConstraintSet.LEFT, cl.getId(), ConstraintSet.LEFT, 120);
                                        set.connect(cb.getId(), ConstraintSet.TOP, cbs.get(i-1).getId(), ConstraintSet.BOTTOM, 20);
                                        set.applyTo(cl);
                                        cb.setText(Tags.get(i).substring(1, Tags.get(i).length() - 1));
                                        cb.setBackgroundResource(button_background2);
                                        cb.setTextColor(Color.parseColor("#FFF6CE"));
                                        cb.setPadding(4, 4, 12, 4);
                                        cbs.add(cb);
                                    }
                                    if (i == 10) {
                                        ConstraintSet set = new ConstraintSet();
                                        CheckBox cb = new CheckBox(AddRecipe.this);
                                        cb.setId(View.generateViewId());
                                        cl.addView(cb);
                                        set.clone(cl);
                                        set.connect(cb.getId(), ConstraintSet.RIGHT, cl.getId(), ConstraintSet.RIGHT, 120);
                                        set.connect(cb.getId(), ConstraintSet.TOP, findViewById(R.id.toolbarAdding).getId(), ConstraintSet.BOTTOM, 40);
                                        set.applyTo(cl);
                                        cb.setText(Tags.get(i).substring(1, Tags.get(i).length() - 1));
                                        cb.setBackgroundResource(button_background2);
                                        cb.setTextColor(Color.parseColor("#FFF6CE"));
                                        cb.setPadding(4, 4, 12, 4);
                                        cbs.add(cb);
                                    }
                                    if (i > 10) {
                                        ConstraintSet set = new ConstraintSet();
                                        CheckBox cb = new CheckBox(AddRecipe.this);
                                        cb.setId(View.generateViewId());
                                        cl.addView(cb);
                                        set.clone(cl);
                                        set.connect(cb.getId(), ConstraintSet.RIGHT, cl.getId(), ConstraintSet.RIGHT, 120);
                                        set.connect(cb.getId(), ConstraintSet.TOP, cbs.get(i-1).getId(), ConstraintSet.BOTTOM, 20);
                                        set.applyTo(cl);
                                        cb.setText(Tags.get(i).substring(1, Tags.get(i).length() - 1));
                                        cb.setBackgroundResource(button_background2);
                                        cb.setTextColor(Color.parseColor("#FFF6CE"));
                                        cb.setPadding(4, 4, 12, 4);
                                        cbs.add(cb);
                                    }
                                }
                            }
                        });
                    }
                }
            };
            ServerCommunication sc = new ServerCommunication();
            sc.lookupCategory(callback);
            counter++;
        }
    }
}
