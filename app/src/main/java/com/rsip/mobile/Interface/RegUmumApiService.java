package com.rsip.mobile.Interface;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegUmumApiService {
    @FormUrlEncoded
    @POST("post_messageDaftar")
    Call<JsonObject> postMessageDaftar(@FieldMap HashMap<String,String> param);
}
