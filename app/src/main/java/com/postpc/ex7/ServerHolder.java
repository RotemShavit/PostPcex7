package com.postpc.ex7;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerHolder {

    private static ServerHolder instance = null;
    public final MyServer myserver;

    synchronized static ServerHolder getInstance()
    {
        if(instance == null)
        {
            instance = new ServerHolder();
        }
        return instance;
    }

    private ServerHolder()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String BASE_URL = "https://hujipostpc2019.pythonanywhere.com";
//        String BASE_URL = "http://10.0.0.23:5678";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient())
                .build();

        this.myserver = retrofit.create(MyServer.class);
    }
}
