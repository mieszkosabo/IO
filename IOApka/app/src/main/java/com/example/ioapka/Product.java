package com.example.ioapka;

import android.text.Spannable;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    private String productName;
    private Spannable nutritionalValues;

    public Product(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        productName = jsonObject.getString("product");
        nutritionalValues = Parser.parseNutritionalValues(jsonObject);
    }

    public String getProductName() {
        return productName;
    }

    public Spannable getNutritionalValues() {
        return nutritionalValues;
    }
}
