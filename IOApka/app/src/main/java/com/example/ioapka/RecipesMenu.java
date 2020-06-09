package com.example.ioapka;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class RecipesMenu extends AppCompatActivity {

    private ImageButton backToMain;
    private Button AddRecipe;
    private Button MyRecipes;
    private Object MainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        backToMain = findViewById(R.id.backToMainButton);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        MyRecipes = findViewById(R.id.MyRecipes);
        MyRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyRecipes();
            }
        });

        AddRecipe = findViewById(R.id.AddRecipe);
        AddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddingRecipe();
            }
        });
    }

    public void openMyRecipes() {
        Intent intent = new Intent(this, MyRec.class);
        startActivity(intent);
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openAddingRecipe() {
        Intent intent = new Intent(this, AddRecipe.class);
        startActivity(intent);
    }
}
