package com.rsip.mobile.Interface;

import com.google.gson.JsonObject;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("post_message")
    Call<JsonObject> postMessage(@FieldMap HashMap<String,String> param);


}
