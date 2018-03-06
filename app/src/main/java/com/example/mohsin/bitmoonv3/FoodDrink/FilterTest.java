package com.example.mohsin.bitmoonv3.FoodDrink;


import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohsin.bitmoonv3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterTest extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        getData();
    }

    public void getData(){
        String url2="http://www.appstudeo.com/bitmoonbackend/public/index.php/api/user/5/vendors?lang=en&page=1&limit=10";
        try {
            String a="[1,2]";
            JSONArray jsonArray=new JSONArray(a);
            Log.d("Array", String.valueOf(jsonArray));
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("type", jsonArray);
            params.put("cuisine",jsonArray);
            params.put("add_details",jsonArray);


            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Response",response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        Log.d("Req", "getBody: "+mRequestBody);
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("client-id", "bitmoon-app-ios");
                    headers.put("Authorization", "5a5df3f8572f5");
                    headers.put("Content-Type","application/json");
                    return headers;
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
