package com.xiver.keycontrollingsystem.network;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private boolean logged;

    public static final String BASE_URL = "http://192.168.1.147:5051";
    private WhoAmIAPI api;

    public Network() {
        this.logged = this.isLogged();
    }

    public boolean isLogged() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(WhoAmIAPI.class);
        doHttpRequest();
        return logged;
    }

    public void doHttpRequest() {
        api.getComplete().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                logged = response.code() == 200;
            }
            @Override
            public void onFailure(Call<Response> call, Throwable throwable) {
                logged = false;
            }
        });
    }
}
