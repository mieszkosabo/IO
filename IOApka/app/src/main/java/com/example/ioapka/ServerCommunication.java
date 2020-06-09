package com.example.ioapka;


import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ServerCommunication {

    private OkHttpClient client;

    public ServerCommunication() {
        client = new OkHttpClient();
    }

    public void lookupEAN(String EAN, Callback cb) {
        System.out.println("jestem");
        String url = "http://86b704fd1623.ngrok.io/lookupEAN=" + EAN;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(cb);
    }

    public void lookupCategory(Callback cb) {
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/Category")
                .build();
        client.newCall(request).enqueue(cb);
    }

    public void lookupIngredients(Callback cb) {
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/Ingredients")
                .build();
        client.newCall(request).enqueue(cb);
    }

    public void lookupUnits(Callback cb) {
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/Units")
                .build();
        client.newCall(request).enqueue(cb);
    }

    public void lookupName(Callback cb) {
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/Recipes")
                .build();
        client.newCall(request).enqueue(cb);
    }

    public void lookupDes(Callback cb) {
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/Recipes/Content")
                .build();
        client.newCall(request).enqueue(cb);
    }

    public void lookupIng(Callback cb) {
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/Recipes/Products")
                .build();
        client.newCall(request).enqueue(cb);
    }
}
