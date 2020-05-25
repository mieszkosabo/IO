package com.example.ioapka;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.material.textfield.TextInputEditText;

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
    private TextInputEditText PreparationT;
    private TextInputEditText CookingT;
    private TextInputEditText Origin;
    private TextInputEditText Serves;
    private TextInputEditText HowMuch;
    private AutoCompleteTextView Ingredient;
    private TextView DiffLabel;
    private Spinner Difficulty;
    private Spinner Unit;
    private Integer counter;
    private ArrayList<String> Tags;
    private ArrayList<CheckBox> cbs;
    private ArrayList<String> IngredientsAdded;
    private String[] Units;
    private ArrayList<Integer> Quantity;
    public ConstraintLayout cl;
    private TableLayout tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_recipes);
        counter = 0;

        AddPhoto = findViewById(R.id.button2);
        MakePhoto = findViewById(R.id.MakePhoto);
        Next = findViewById(R.id.Next);
        Prev = findViewById(R.id.Prev);

        DiffLabel = findViewById(R.id.DiffLabel);
        Difficulty = findViewById(R.id.Difficulty);

        Origin = findViewById(R.id.Origin);
        Serves = findViewById(R.id.Serves);

        PreparationT = findViewById(R.id.PreparationT);
        CookingT = findViewById(R.id.CookingT);

        HowMuch = findViewById(R.id.HowMuch);
        Ingredient = findViewById(R.id.Ingredient);

        Unit = findViewById(R.id.Unit);

        tb = findViewById(R.id.Ingredients);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddRecipe.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.diffTable));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Difficulty.setAdapter(myAdapter);

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
                        cbs = new ArrayList<CheckBox>();
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
        else if (counter == 1) {
            for (int i = 0; i < cbs.size(); i++) {
                cbs.get(i).setVisibility(View.INVISIBLE);
            }
            Origin.setVisibility(View.VISIBLE);
            Serves.setVisibility(View.VISIBLE);
            CookingT.setVisibility(View.VISIBLE);
            PreparationT.setVisibility(View.VISIBLE);
            counter++;
        }
        else if (counter == 2) {
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
                        Units = myResponse.split(",");
                        AddRecipe.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < Units.length; i++) {
                                    Units[i] = Units[i].substring(1, Units[i].length() - 1);
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddRecipe.this, android.R.layout.simple_dropdown_item_1line, Units);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                Unit.setAdapter(adapter);
                            }
                        });
                    }
                }
            };
            ServerCommunication sc = new ServerCommunication();
            sc.lookupUnits(callback);
            Origin.setVisibility(View.INVISIBLE);
            Serves.setVisibility(View.INVISIBLE);
            CookingT.setVisibility(View.INVISIBLE);
            PreparationT.setVisibility(View.INVISIBLE);
            DiffLabel.setVisibility(View.VISIBLE);
            Difficulty.setVisibility(View.VISIBLE);
            counter++;
        }
        else if (counter == 3) {
            Callback callback = new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException{
                    if (response.isSuccessful()) {
                        String myResponse = response.body().string();
                        myResponse = myResponse.substring(1, myResponse.length() - 1);
                        final String[] ingredients = myResponse.split(",");
                        AddRecipe.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < ingredients.length; i++) {
                                    ingredients[i] = ingredients[i].substring(1, ingredients[i].length() - 1);
                                }
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddRecipe.this, android.R.layout.simple_dropdown_item_1line, ingredients);
                                Ingredient.setThreshold(1);
                                Ingredient.setAdapter(adapter);
                                Ingredient.setVisibility(View.VISIBLE);
                                DiffLabel.setVisibility(View.INVISIBLE);
                                Difficulty.setVisibility(View.INVISIBLE);
                                HowMuch.setVisibility(View.VISIBLE);
                                Unit.setVisibility(View.VISIBLE);
                                tb.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }
            };
            ServerCommunication sc = new ServerCommunication();
            sc.lookupIngredients(callback);
        }
    }
}
