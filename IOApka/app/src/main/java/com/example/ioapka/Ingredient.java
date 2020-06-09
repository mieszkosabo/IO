package com.example.ioapka;

public class Ingredient {
    private String ingredient;
    private Integer Quantity;
    private String Unit;

    Ingredient(String ingredient, Integer Quantity, String Unit) {
        this.ingredient = ingredient;
        this.Quantity = Quantity;
        this.Unit = Unit;
    }

    public String getIngredient() {
        return ingredient;
    }
}
