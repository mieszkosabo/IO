package com.example.ioapka;

import android.widget.Button;
import android.widget.TextView;

public class TableIngredient {
    private TextView Ingredient;
    private TextView Quantity;
    private Button Delete;

    TableIngredient(TextView Ingredient, TextView Quantity, Button Delete) {
        this.Ingredient = Ingredient;
        this.Quantity = Quantity;
        this.Delete = Delete;
    }
}
