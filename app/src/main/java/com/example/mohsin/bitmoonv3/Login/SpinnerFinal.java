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
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_spinner_item;


public class SpinnerFinal extends Activity implements AdapterView.OnItemSelectedListener{
    final Context context = this;
    String country_url="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/countries?lang=en";
    String city_url="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/cities?lang=en&country=";
    String city_url2;
    String token;

    List<String> country_list = new ArrayList<String>();
    List<String> city_list = new ArrayList<String>();
    Spinner city_spinner;
    ArrayAdapter<String> city_adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new1);

        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();

        Spinner spinner = (Spinner) findViewById(R.id.test);
        spinner.setOnItemSelectedListener(this);

        city_spinner=(Spinner)findViewById(R.id.test2);
        city_spinner.setOnItemSelectedListener(this);
        getData();

        country_list.add("Jordan");
        city_list.add("");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, simple_spinner_item, country_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        city_adapter = new ArrayAdapter<String>(this, simple_spinner_item, city_list);
        city_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_spinner.setAdapter(city_adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.test)
        {
            String country = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), "Selected: " + country, Toast.LENGTH_SHORT).show();
            city_url2=city_url+country;
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, country_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            country_list.clear();
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String name = actor.getString("country_name");
                                Log.i("Response",name);
                                country_list.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SpinnerFinal.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization", "5a2e332986ffc");
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        };
        MySingleton.getmInstance(SpinnerFinal.this).addToRequestque(stringRequest);
    }


    public void getData2()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, city_url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            city_list.clear();
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String name = actor.getString("city_name");
                                Log.i("Response",name);
                                city_list.add(name);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SpinnerFinal.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization", "5a2e332986ffc");
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        };
        MySingleton.getmInstance(SpinnerFinal.this).addToRequestque(stringRequest);
    }
}


