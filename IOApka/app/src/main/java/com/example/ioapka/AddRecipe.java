package com.example.ioapka;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import static com.example.ioapka.R.drawable.button_background_delete;

public class AddRecipe extends AppCompatActivity {

    private Button Next;
    private Button AddPhoto;
    private Button MakePhoto;
    private Button Prev;
    private Button AddIngredient;
    private Button OK;
    private ArrayList<TableIngredient> TableRows;
    private ArrayList<Ingredient> IngredientsAdded;
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
    private String[] Units;
    private String[] ingredients;
    public ConstraintLayout cl;
    private ScrollView tb;
    private TextView Popup;
    private TableLayout tableIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_recipes);
        counter = 0;

        TableRows = new ArrayList<TableIngredient>();
        IngredientsAdded = new ArrayList<Ingredient>();
        AddPhoto = findViewById(R.id.button2);
        MakePhoto = findViewById(R.id.MakePhoto);
        Next = findViewById(R.id.Next);
        Prev = findViewById(R.id.Prev);
        AddIngredient = findViewById(R.id.AddIngredient);
        OK = findViewById(R.id.OK);

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
        tableIngredients = findViewById(R.id.tableIngredients);

        Popup = findViewById(R.id.Popup);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddRecipe.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.diffTable));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Difficulty.setAdapter(myAdapter);

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOK();
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnNext();
            }
        });

        AddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAddIngredient();
            }
        });

        cl = findViewById(R.id.MyLayout);
    }

    public void OnOK() {
        Popup.setVisibility(View.INVISIBLE);
        OK.setVisibility(View.INVISIBLE);
        AddIngredient.setVisibility(View.VISIBLE);
        tb.setVisibility(View.VISIBLE);
    }

    public void OnAddIngredient() {
        String ingr = Ingredient.getText().toString();
        if (checkIngredient(ingr)) {
            String quantity = HowMuch.getText().toString();
            if(quantity.equals("")) {
                Popup.setText("Należy podać jakąś ilość");
                Popup.setVisibility(View.VISIBLE);
                OK.setVisibility(View.VISIBLE);
                AddIngredient.setVisibility(View.INVISIBLE);
                tb.setVisibility(View.INVISIBLE);
            }
            else {
                Integer qtity = Integer.parseInt(HowMuch.getText().toString());
                String unit = Unit.getSelectedItem().toString();
                String toDisplay = qtity.toString() + " " + unit;
                Ingredient toAdd = new Ingredient(ingr, qtity, unit);
                IngredientsAdded.add(toAdd);
                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                tr.setWeightSum(100f);
                TextView tv1 = TextInRow(0, 60f, ingr);
                TextView tv2 = TextInRow(1, 30f, toDisplay);
                Button btn = ButtonInRow();
                tr.addView(tv1);
                tr.addView(tv2);
                tr.addView(btn);
                tableIngredients.addView(tr);
            }
        }
        else {
            Popup.setText("Należy podać produkt z listy");
            Popup.setVisibility(View.VISIBLE);
            OK.setVisibility(View.VISIBLE);
            AddIngredient.setVisibility(View.INVISIBLE);
            tb.setVisibility(View.INVISIBLE);
        }
    }

    private TextView TextInRow(int column, float weight, String inside) {
        TextView tv = new TextView(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(column);
        params.height = 100;
        params.width = 0;
        params.weight = weight;
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setText(inside);
        tv.setTextColor(Color.parseColor("#FFF6CE"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        tv.setBackgroundResource(button_background2);
        return tv;
    }

    private Button ButtonInRow() {
        Button bt = new Button(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(2);
        params.width = 0;
        params.height = 100;
        params.weight = 10f;
        bt.setLayoutParams(params);
        bt.setText("X");
        bt.setBackgroundResource(button_background_delete);
        bt.setTextColor(Color.parseColor("#FFF6CE"));
        bt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        return bt;
    }

    private boolean checkIngredient(String ingredient) {
        for (int i = 0; i < ingredients.length; i++) {
            if (ingredient.equals(ingredients[i])) {
                return true;
            }
        }
        return false;
    }

    public void OnNext() {
        if (counter == 0)  {
            Prev.setVisibility(View.VISIBLE);
            AddPhoto.setVisibility(View.INVISIBLE);
            MakePhoto.setVisibility(View.INVISIBLE);
            cbs = new ArrayList<CheckBox>();

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
                        ingredients = myResponse.split(",");
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
                                AddIngredient.setVisibility(View.VISIBLE);
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
