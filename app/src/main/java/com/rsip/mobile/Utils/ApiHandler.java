package com.rsip.mobile.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rsip.mobile.Interface.ApiService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {

    public static final String BASE_URL=Koneksi.URL_REG_PASIEN_BPJS;

    private static ApiService apiService;
    public static ApiService getApiService(){
        if (apiService==null){
            Gson gson=new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson)).baseUrl(BASE_URL).build();
            apiService=retrofit.create(apiService.getClass());
            return apiService;
        }
        else
            return apiService;
    }

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass,String baseUrl){
        Retrofit builder=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return builder.create(serviceClass);
    }
}
