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
        String url = "http://10.0.2.2:8080/lookupEAN=" + EAN;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(cb);
    }

}
