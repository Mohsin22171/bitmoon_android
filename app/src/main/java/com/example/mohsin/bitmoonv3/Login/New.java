package com.example.mohsin.bitmoonv3.Login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_spinner_item;


public class New extends Activity implements AdapterView.OnItemSelectedListener{
    final Context context = this;
    String url="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/countries?lang=en";
    String url2="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/cities?lang=en&country=";
    String url3;


    List<String> categories = new ArrayList<String>();
    List<String> categories2 = new ArrayList<String>();
    Spinner spinner2;
    ArrayAdapter<String> dataAdapter2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new1);

        Spinner spinner = (Spinner) findViewById(R.id.test);
        spinner.setOnItemSelectedListener(this);

        spinner2=(Spinner)findViewById(R.id.test2);
        spinner2.setOnItemSelectedListener(this);
        getData();

        categories.add("Jordan");
        categories2.add("");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        dataAdapter2 = new ArrayAdapter<String>(this, simple_spinner_item, categories2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.test)
        {
            String country = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), "Selected: " + country, Toast.LENGTH_SHORT).show();
            url3=url2+country;
            getData2();
        }
        else if(spinner.getId() == R.id.test2)
        {
            String currency = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), "Selected: " + currency, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void getData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            categories.clear();
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String name = actor.getString("country_name");
                                Log.i("Response",name);
                                categories.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(New.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization", "5a16bd592018f");
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }

            /*@Override
            public String getBodyContentType()
            {
                return "application/json";
            }*/
        };

        MySingleton.getmInstance(New.this).addToRequestque(stringRequest);
    }


    public void getData2()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            categories2.clear();
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String name = actor.getString("city_name");
                                Log.i("Response",name);
                                categories2.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(New.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization", "5a16bd592018f");
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }

            /*@Override
            public String getBodyContentType()
            {
                return "application/json";
            }*/
        };

        MySingleton.getmInstance(New.this).addToRequestque(stringRequest);
    }
}

