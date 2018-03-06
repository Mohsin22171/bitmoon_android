package com.example.mohsin.bitmoonv3.CustomerProfile;

import android.app.Activity;
import android.os.Bundle;
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
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.CurrencyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpinnerTest extends Activity implements AdapterView.OnItemSelectedListener{
    CurrencyAdapter adapter = null;
    ArrayList<CurrencyModel> currency = null;
    ArrayList<CurrencyModel> currency1 = null;
    String token;
    String country_id,currency_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_test);

        final Spinner mySpinner = (Spinner) findViewById( R.id.spinner_currency) ;
        final Spinner mySpinner1 = (Spinner) findViewById( R.id.spinner_country) ;

        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();

        currency1 = new ArrayList<>();
        currency1 = populateCustomerData1(currency1);
        adapter = new CurrencyAdapter(this, currency1);
        mySpinner1.setAdapter(adapter);
        mySpinner1.setOnItemSelectedListener(this);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        country_id = parent.getItemAtPosition(position).toString();
        Log.d("CountryId",country_id);

        currency_id = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    private ArrayList<CurrencyModel> populateCustomerData1(final ArrayList<CurrencyModel> currency1) {
        String url="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/countries?lang=en";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.i("Response",response1);
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String name = actor.getString("country_flag");
                                Log.i("Response",name);
                                CurrencyModel currencyModel=new CurrencyModel();
                                currencyModel.setProfilePic(name);
                                currencyModel.setFirstName(actor.getString("country_id"));
                                currency1.add(currencyModel);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SpinnerTest.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(SpinnerTest.this).addToRequestque(stringRequest);
        return currency1;
    }

}
