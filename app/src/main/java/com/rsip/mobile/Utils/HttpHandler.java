package com.rsip.mobile.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHandler {
    public String sendPostRequest(String reqUrl, HashMap<String ,String> postData){
        URL url;
        StringBuilder sb = new StringBuilder();
        try{
            url=new URL(reqUrl);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream ou=connection.getOutputStream();
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(ou,"UTF-8"));
            writer.write(getPostDataString(postData));
            writer.flush();
            writer.close();
            ou.close();
            int responsecode=connection.getResponseCode();
            if (responsecode==HttpURLConnection.HTTP_OK){
                BufferedReader br =new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb=new StringBuilder();
                String response;
                while ((response=br.readLine())!=null){
                    sb.append(response);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();

    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
