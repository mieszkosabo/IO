package com.example.ioapka;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import org.json.JSONException;
import org.json.JSONObject;

public class Parser {
    private static void addBold(SpannableStringBuilder spannable, String text) {
        int startIndex = spannable.length();
        spannable.append(text);
        spannable.setSpan(new StyleSpan(Typeface.BOLD), startIndex, spannable.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private static void addValue(SpannableStringBuilder spannable, JSONObject jsonObject,
                                 String index, String value) throws JSONException {
        addBold(spannable, value + ": ");
        spannable.append(jsonObject.getString(index) + "\n");
    }

    public static Spannable parseNutritionalValues(JSONObject jsonObject) throws JSONException {
        SpannableStringBuilder spannable = new SpannableStringBuilder();
        addBold(spannable, "Wartości odżywcze (na 100 gramów):\n");
        addValue(spannable, jsonObject, "kcal", "kilokalorie");
        addValue(spannable, jsonObject, "energy", "wartość energetyczna");
        addValue(spannable, jsonObject, "fats", "tłuszcz");
        addValue(spannable, jsonObject, "saturates", "kwasy tłuszczowe nasycone");
        addValue(spannable, jsonObject, "carbohydrates", "węglowodany");
        addValue(spannable, jsonObject, "sugars", "cukry");
        addValue(spannable, jsonObject, "proteins", "białko");
        addValue(spannable, jsonObject, "salts", "sól");
        return spannable;
    }
}
