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
import android.widget.EditText;
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
    private Button OK2;
    private Button OK3;
    private Button AddRec;

    private ArrayList<TableIngredient> TableRows;
    private ArrayList<Ingredient> IngredientsAdded;
    private ArrayList<String> Tags;
    private ArrayList<CheckBox> cbs;

    private EditText Description;

    private TextInputEditText PreparationT;
    private TextInputEditText CookingT;
    private TextInputEditText Origin;
    private TextInputEditText Serves;
    private TextInputEditText HowMuch;
    private TextInputEditText Name;

    private AutoCompleteTextView Ingredient;

    private Spinner Difficulty;
    private Spinner Unit;

    private Integer counter;
    private Integer tagSites;
    private Integer tagCounter;

    private String[] Units;
    private String[] ingredients;

    private ArrayList<Boolean> first;

    public ConstraintLayout cl;

    private TableLayout tableIngredients;

    private ScrollView tb;

    private TextView Popup;
    private TextView Popup2;
    private TextView Popup3;
    private TextView Popup4;
    private TextView DiffLabel;

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
        OK2 = findViewById(R.id.OK2);
        OK3 = findViewById(R.id.OK3);
        AddRec = findViewById(R.id.AddRec);

        DiffLabel = findViewById(R.id.DiffLabel);
        Difficulty = findViewById(R.id.Difficulty);

        Origin = findViewById(R.id.Origin);
        Serves = findViewById(R.id.Serves);

        PreparationT = findViewById(R.id.PreparationT);
        CookingT = findViewById(R.id.CookingT);
        HowMuch = findViewById(R.id.HowMuch);
        Ingredient = findViewById(R.id.Ingredient);
        Unit = findViewById(R.id.Unit);

        Name = findViewById(R.id.Name);

        tb = findViewById(R.id.Ingredients);
        tableIngredients = findViewById(R.id.tableIngredients);

        Description = findViewById(R.id.Description);

        cbs = new ArrayList<CheckBox>();

        first = new ArrayList<Boolean>();
        first.add(true);

        Popup = findViewById(R.id.Popup);
        Popup2 = findViewById(R.id.Popup2);
        Popup3 = findViewById(R.id.Popup3);
        Popup4 = findViewById(R.id.Popup4);
        tagSites = 1;
        tagCounter = 0;

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

        OK2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOK2();
            }
        });

        OK3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnOK3();
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

        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnPrev();
            }
        });

        AddRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnAddRecipe();
            }
        });
        cl = findViewById(R.id.MyLayout);
    }

    public void OnAddRecipe() {
        Prev.setVisibility(View.INVISIBLE);
        AddRec.setVisibility(View.INVISIBLE);
        Description.setVisibility(View.INVISIBLE);
        Popup4.setVisibility(View.VISIBLE);
    }

    public void OnPrev() {
        if (counter == tagSites + 4) {
            Ingredient.setVisibility(View.VISIBLE);
            HowMuch.setVisibility(View.VISIBLE);
            Unit.setVisibility(View.VISIBLE);
            tb.setVisibility(View.VISIBLE);
            AddIngredient.setVisibility(View.VISIBLE);
            Next.setVisibility(View.VISIBLE);
            AddRec.setVisibility(View.INVISIBLE);
            Description.setVisibility(View.INVISIBLE);
            counter--;
        }
        else if (counter == tagSites + 3) {
            Ingredient.setVisibility(View.INVISIBLE);
            DiffLabel.setVisibility(View.VISIBLE);
            Difficulty.setVisibility(View.VISIBLE);
            HowMuch.setVisibility(View.INVISIBLE);
            Unit.setVisibility(View.INVISIBLE);
            tb.setVisibility(View.INVISIBLE);
            AddIngredient.setVisibility(View.INVISIBLE);
            counter--;
        }
        else if (counter == tagSites + 2) {
            Origin.setVisibility(View.VISIBLE);
            Serves.setVisibility(View.VISIBLE);
            CookingT.setVisibility(View.VISIBLE);
            PreparationT.setVisibility(View.VISIBLE);
            DiffLabel.setVisibility(View.INVISIBLE);
            Difficulty.setVisibility(View.INVISIBLE);
            counter--;
        }
        else if (counter == tagSites + 1) {
            Origin.setVisibility(View.INVISIBLE);
            Serves.setVisibility(View.INVISIBLE);
            CookingT.setVisibility(View.INVISIBLE);
            PreparationT.setVisibility(View.INVISIBLE);
            int constraint = tagCounter % 20;
            if (constraint == 0) {
                constraint = tagCounter - 20;
            }
            else {
                constraint = tagCounter - constraint;
            }
            for (int i = tagCounter - 1; i >= constraint; i--) {
                cbs.get(i).setVisibility(View.VISIBLE);
            }
            counter--;
        }
        else if (counter > 1) {
            int constraint = Math.min(tagCounter + 20, Tags.size());
            int ConstraintUp = tagCounter - 1;
            tagCounter = tagCounter - tagCounter % 20;
            for (int i = tagCounter; i < constraint; i++) {
                cbs.get(i).setVisibility(View.INVISIBLE);
            }
            constraint = tagCounter % 20;
            constraint = tagCounter - constraint;
            constraint = tagCounter - 20;
            for (int i = tagCounter - 1; i >= constraint; i--) {
                cbs.get(i).setVisibility(View.VISIBLE);
            }
            if (counter < tagSites) {
                tagCounter = tagCounter - 20;
            }
            counter--;
        }
        else if (counter == 1) {
            for (int i = 0; i < Math.min(20, Tags.size()); i++) {
                cbs.get(i).setVisibility(View.INVISIBLE);
            }
            tagCounter = 0;
            Prev.setVisibility(View.INVISIBLE);
            AddPhoto.setVisibility(View.VISIBLE);
            MakePhoto.setVisibility(View.VISIBLE);
            Name.setVisibility(View.VISIBLE);
            counter--;
        }
    }

    public void OnOK3() {
        Popup3.setVisibility(View.INVISIBLE);
        OK3.setVisibility(View.INVISIBLE);
        Next.setVisibility(View.VISIBLE);
        AddPhoto.setVisibility(View.VISIBLE);
        MakePhoto.setVisibility(View.VISIBLE);
    }

    public void OnOK2() {
        Popup2.setVisibility(View.INVISIBLE);
        OK2.setVisibility(View.INVISIBLE);
        Origin.setVisibility(View.VISIBLE);
        Serves.setVisibility(View.VISIBLE);
        CookingT.setVisibility(View.VISIBLE);
        PreparationT.setVisibility(View.VISIBLE);
        Next.setVisibility(View.VISIBLE);
        Prev.setVisibility(View.VISIBLE);
    }

    public void OnOK() {
        Popup.setVisibility(View.INVISIBLE);
        OK.setVisibility(View.INVISIBLE);
        AddIngredient.setVisibility(View.VISIBLE);
        tb.setVisibility(View.VISIBLE);
        Next.setVisibility(View.VISIBLE);
        Prev.setVisibility(View.VISIBLE);
    }

    public void OnDelete(Ingredient toDelete, TableRow toDel) {
        IngredientsAdded.remove(toDelete);
        tableIngredients.removeView(toDel);
    }

    public boolean alreadyAdded(String ingredient) {
        for (int i = 0; i < IngredientsAdded.size(); i++) {
            if (ingredient.equals(IngredientsAdded.get(i).getIngredient())) {
                return true;
            }
        }
        return false;
    }

    public void OnAddIngredient() {
        String ingr = Ingredient.getText().toString();
        if (checkIngredient(ingr)) {
            String quantity = HowMuch.getText().toString();
            if (alreadyAdded(ingr)) {
                Popup.setText("Składnik już dodany");
                Popup.setVisibility(View.VISIBLE);
                OK.setVisibility(View.VISIBLE);
                AddIngredient.setVisibility(View.INVISIBLE);
                tb.setVisibility(View.INVISIBLE);
                Next.setVisibility(View.INVISIBLE);
                Prev.setVisibility(View.INVISIBLE);
            }
            else if(quantity.equals("")) {
                Popup.setText("Należy podać jakąś ilość");
                Popup.setVisibility(View.VISIBLE);
                OK.setVisibility(View.VISIBLE);
                AddIngredient.setVisibility(View.INVISIBLE);
                tb.setVisibility(View.INVISIBLE);
                Next.setVisibility(View.INVISIBLE);
                Prev.setVisibility(View.INVISIBLE);
            }
            else {
                Integer qtity = Integer.parseInt(HowMuch.getText().toString());
                String unit = Unit.getSelectedItem().toString();
                String toDisplay = qtity.toString() + " " + unit;
                final Ingredient toAdd = new Ingredient(ingr, qtity, unit);
                IngredientsAdded.add(toAdd);
                final TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                tr.setWeightSum(100f);
                TextView tv1 = TextInRow(0, 60f, ingr);
                TextView tv2 = TextInRow(1, 30f, toDisplay);
                Button btn = ButtonInRow();
                btn.setOnClickListener(new ClickListener(toAdd, tr) {
                    @Override
                    public void onClick(View v) {
                        OnDelete(toAdd, tr);
                    }
                });
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
            Next.setVisibility(View.INVISIBLE);
            Prev.setVisibility(View.INVISIBLE);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void newTag(int indexT, int indexLP, int index) {
        ConstraintSet set = new ConstraintSet();
        CheckBox cb = new CheckBox(AddRecipe.this);
        cb.setId(View.generateViewId());
        cl.addView(cb);
        set.clone(cl);

        if (indexLP == 0) {
            set.connect(cb.getId(), ConstraintSet.LEFT, cl.getId(), ConstraintSet.LEFT, 120);
        }
        else {
            set.connect(cb.getId(), ConstraintSet.RIGHT, cl.getId(), ConstraintSet.RIGHT, 120);
        }

        if (indexT == 0) {
            set.connect(cb.getId(), ConstraintSet.TOP, findViewById(R.id.toolbarAdding).getId(), ConstraintSet.BOTTOM, 40);
        }
        else {
            set.connect(cb.getId(), ConstraintSet.TOP, cbs.get(index-1).getId(), ConstraintSet.BOTTOM, 20);
        }

        set.applyTo(cl);
        cb.setText(Tags.get(index).substring(1, Tags.get(index).length() - 1));
        cb.setVisibility(View.INVISIBLE);
        cb.setBackgroundResource(button_background2);
        cb.setTextColor(Color.parseColor("#FFF6CE"));
        cb.setPadding(4, 4, 12, 4);
        cbs.add(cb);
    }

    public void OnNext() {
        if (counter < tagSites)  {
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
                        tagSites = Tags.size() / 20;
                        if (Tags.size() % 20 > 0) {
                            tagSites++;
                        }

                        for (int i = 0; i < tagSites + 2; i++) {
                            first.add(true);
                        }
                        tagCounter = Math.min(Tags.size(), 20);
                        AddRecipe.this.runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                            @Override
                            public void run() {
                                for (int i = 0; i < Tags.size(); i++) {
                                    if (i % 20 == 0) {
                                        newTag(0, 0, i);
                                    }
                                    if (i % 20 > 0 && i % 20 < 10) {
                                        newTag(1, 0, i);
                                    }
                                    if (i % 20 == 10) {
                                        newTag(0, 1, i);

                                    }
                                    if (i % 20 > 10) {
                                        newTag(1, 1, i);
                                    }
                                }
                                int constraint = Math.min(tagCounter + 20, Tags.size());
                                for (int i = 0; i < constraint; i++) {
                                    cbs.get(i).setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                    else {
                        Next.setVisibility(View.INVISIBLE);
                        Prev.setVisibility(View.INVISIBLE);
                    }
                }
            };
            if (Name.getText().toString().equals("")) {
                MakePhoto.setVisibility(View.INVISIBLE);
                AddPhoto.setVisibility(View.INVISIBLE);
                Next.setVisibility(View.INVISIBLE);
                Popup3.setVisibility(View.VISIBLE);
                OK3.setVisibility(View.VISIBLE);
            }
            else {
                if (counter == 0) {
                    Prev.setVisibility(View.VISIBLE);
                    AddPhoto.setVisibility(View.INVISIBLE);
                    MakePhoto.setVisibility(View.INVISIBLE);
                    Name.setVisibility(View.INVISIBLE);
                    if (first.get(counter)) {
                        ServerCommunication sc = new ServerCommunication();
                        sc.lookupCategory(callback);
                        first.set(counter, false);
                    }
                    else {
                        for (int i = 0; i < Math.min(Tags.size(), 20); i++) {
                            cbs.get(i).setVisibility(View.VISIBLE);
                            tagCounter++;
                        }
                    }
                }
                else {
                    if (tagCounter > 0) {
                        for (int i = tagCounter - 1; i >= tagCounter - 20; i--) {
                            cbs.get(i).setVisibility(View.INVISIBLE);
                        }
                    }
                    int constraint = Math.min(tagCounter + 20, Tags.size());
                    for (int i = tagCounter; i < constraint; i++) {
                        cbs.get(i).setVisibility(View.VISIBLE);
                        tagCounter++;
                    }
                }
                counter++;
            }
        }
        else if (counter == tagSites) {
            for (int i = 0; i < cbs.size(); i++) {
                cbs.get(i).setVisibility(View.INVISIBLE);
            }
            Origin.setVisibility(View.VISIBLE);
            Serves.setVisibility(View.VISIBLE);
            CookingT.setVisibility(View.VISIBLE);
            PreparationT.setVisibility(View.VISIBLE);
            counter++;
        }
        else if (counter == tagSites + 1) {
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
            if (CookingT.getText().toString().equals("") || PreparationT.getText().toString().equals("")) {
                Origin.setVisibility(View.INVISIBLE);
                Serves.setVisibility(View.INVISIBLE);
                CookingT.setVisibility(View.INVISIBLE);
                PreparationT.setVisibility(View.INVISIBLE);
                Popup2.setVisibility(View.VISIBLE);
                OK2.setVisibility(View.VISIBLE);
                Next.setVisibility(View.INVISIBLE);
                Prev.setVisibility(View.INVISIBLE);
            }
            else {
                if (first.get(counter)) {
                    ServerCommunication sc = new ServerCommunication();
                    sc.lookupUnits(callback);
                    first.set(counter, false);
                }
                Origin.setVisibility(View.INVISIBLE);
                Serves.setVisibility(View.INVISIBLE);
                CookingT.setVisibility(View.INVISIBLE);
                PreparationT.setVisibility(View.INVISIBLE);
                DiffLabel.setVisibility(View.VISIBLE);
                Difficulty.setVisibility(View.VISIBLE);
                counter++;
            }
        }
        else if (counter == tagSites + 2) {
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
            if (first.get(counter)) {
                ServerCommunication sc = new ServerCommunication();
                sc.lookupIngredients(callback);
                first.set(counter, false);
            }
            else {
                Ingredient.setVisibility(View.VISIBLE);
                DiffLabel.setVisibility(View.INVISIBLE);
                Difficulty.setVisibility(View.INVISIBLE);
                HowMuch.setVisibility(View.VISIBLE);
                Unit.setVisibility(View.VISIBLE);
                tb.setVisibility(View.VISIBLE);
                AddIngredient.setVisibility(View.VISIBLE);
            }

            counter++;
        }
        else if (counter == tagSites + 3) {
            Ingredient.setVisibility(View.INVISIBLE);
            HowMuch.setVisibility(View.INVISIBLE);
            Unit.setVisibility(View.INVISIBLE);
            tb.setVisibility(View.INVISIBLE);
            AddIngredient.setVisibility(View.INVISIBLE);
            Next.setVisibility(View.INVISIBLE);
            AddRec.setVisibility(View.VISIBLE);
            Description.setVisibility(View.VISIBLE);
            counter++;
        }
    }
}
