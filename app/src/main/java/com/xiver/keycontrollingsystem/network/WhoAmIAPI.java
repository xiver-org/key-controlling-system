package com.xiver.keycontrollingsystem.network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WhoAmIAPI {
    @GET("/user/self/who_am_i")
    Call<Response> getComplete();
}
