package com.rsip.mobile.Interface;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface WebServiceBPJS {

    @POST("ApiUrlpath")
    @FormUrlEncoded
    @Headers("x-token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlJTSVAifQ.GZdOrhQbcTgJ2FIV4wRnqFXd5I0AtG7CDUI_RCWn8EM")
    Call<JsonObject> ApiName(@Body JsonObject jsonBody);
}
