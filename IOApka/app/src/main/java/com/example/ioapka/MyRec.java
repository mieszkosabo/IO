package com.example.ioapka;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.ioapka.R.drawable.button_background2;

public class MyRec extends AppCompatActivity {

    private TextView Popup;
    private TextView Popup2;

    private TableLayout tableIngredients;

    private ArrayList<String> Ingredients;
    private ArrayList<String> Quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrec);

        Ingredients = new ArrayList<String>();
        Quantity = new ArrayList<String>();

        tableIngredients = findViewById(R.id.tableIngredients2);

        Popup = findViewById(R.id.Popup5);
        Popup2 = findViewById(R.id.Popup6);

        searchName();
        searchDes();
        searchIng();
    }

    public void addRows() {
        for (int i = 0; i < Ingredients.size(); i++) {
            final TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tr.setWeightSum(100f);
            TextView tv1 = TextInRow(0, 60f, Ingredients.get(i));
            TextView tv2 = TextInRow(1, 40f, Quantity.get(i));
            tr.addView(tv1);
            tr.addView(tv2);
            tableIngredients.addView(tr);
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

    public void searchName() {
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
                    Popup.setText(myResponse);
                }
            }
        };

        ServerCommunication sc = new ServerCommunication();
        sc.lookupName(callback);
    }

    public void searchDes() {
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                if (response.isSuccessful()) {
                    String myResponse = response.body().string();
                    Popup2.setText(myResponse);
                }
            }
        };

        ServerCommunication sc = new ServerCommunication();
        sc.lookupDes(callback);
    }

    public void searchIng() {
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
                    ArrayList<String> Ingr = new ArrayList<String>(Arrays.asList(myResponse.split("[}][,][{]")));
                    Ingr.set(0, Ingr.get(0) + "}");
                    Ingr.set(1, "{" + Ingr.get(1));
                    Popup2.setText(Ingr.get(1));

                    try {
                        for (int i = 0; i < Ingr.size(); i++) {
                            JSONObject obj = new JSONObject(Ingr.get(i));
                            Ingredients.add(obj.getString("rawProduct"));
                            Quantity.add(obj.getString("amount") + " " + obj.getString("unit"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    addRows();

                }
            }
        };

        ServerCommunication sc = new ServerCommunication();
        sc.lookupIng(callback);
    }

}
