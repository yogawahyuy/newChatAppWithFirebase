package com.rsip.mobile.Interface;

import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("post_message")
    Call<JsonObject> postMessage(@FieldMap HashMap<String,String> param);

    @FormUrlEncoded
    @Headers("x-token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlJTSVAifQ.GZdOrhQbcTgJ2FIV4wRnqFXd5I0AtG7CDUI_RCWn8EM")
    @POST("post_bpjs")
    Call<JsonObject> postBpjs(@FieldMap HashMap<String,String > param);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "x-token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlJTSVAifQ.GZdOrhQbcTgJ2FIV4wRnqFXd5I0AtG7CDUI_RCWn8EM"
    })
    @POST("post_antrianBpjs")
    Call<JsonObject> postRawJSON(@Body JsonObject jsonObject);

    @FormUrlEncoded
    @POST("post_DaftarAntrian")
    Call<JsonObject> postDaftarAntrian(@FieldMap HashMap<String,String> param);

    @FormUrlEncoded
    @POST("post_BatalDaftarUmum")
    Call<JsonObject> postBatalDaftarUmum(@FieldMap HashMap<String,String> param);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "x-token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IlJTSVAifQ.GZdOrhQbcTgJ2FIV4wRnqFXd5I0AtG7CDUI_RCWn8EM"
    })
    @POST("post_jadwalOperasi")
    Call<JsonObject> postJSONOperasi(@Body JsonObject jsonObject);

}
