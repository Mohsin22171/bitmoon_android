package com.example.mohsin.bitmoonv3.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class RegisterFinal2 extends Activity implements AdapterView.OnItemSelectedListener{
    Spinner spinnerCountry;
    ImageView Register;
    String city;
    String country;
    EditText Email,Password,CPassword,FName,LName;
    String email,password,cpassword,fName,lName;
    AlertDialog.Builder builder;
    String url="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/signup";
    String checkbox_result="false";

    String country_url="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/countries?lang=en";
    String city_url="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/cities?lang=en&country=";
    String city_url2;
    List<String> country_list = new ArrayList<String>();
    List<String> city_list = new ArrayList<String>();
    Spinner city_spinner;
    ArrayAdapter<String> city_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);
        CPassword = (EditText) findViewById(R.id.CPassword);
        FName = (EditText) findViewById(R.id.FName);
        LName = (EditText) findViewById(R.id.LName);
        builder = new AlertDialog.Builder(RegisterFinal2.this);
        Register=(ImageView)findViewById(R.id.register);

        if (!new PrefManager(this).isUserLogedOut()) {
            //user's email and password both are saved in preferences
            startHomeActivity();
        }

       /* Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=Email.getText().toString();
                password=Password.getText().toString();
                cpassword=CPassword.getText().toString();
                fName=FName.getText().toString();
                lName=LName.getText().toString();
                if(email.equals("")||password.equals("")||cpassword.equals("")||fName.equals("")||lName.equals(""))
                {
                    builder.setTitle("Something Went Wrong");
                    builder.setMessage("Please Fill all the fields");
                    displayAlert("input_error");
                }
                else {
                    getData();
                }
            }
        });*/

        spinnerCountry = (Spinner) findViewById(R.id.jordan);
        spinnerCountry.setOnItemSelectedListener(this);

        city_spinner = (Spinner) findViewById(R.id.CitySpinner);
        city_spinner.setOnItemSelectedListener(this);
        Register=(ImageView) findViewById(R.id.register);
        getData2();

        country_list.add("Jordan");
        city_list.add("");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, simple_spinner_item, country_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(dataAdapter);

        city_adapter = new ArrayAdapter<String>(this, simple_spinner_item, city_list);
        city_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_spinner.setAdapter(city_adapter);

    }

    public void getData(){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("email",email);
            params.put("password",password);
            params.put("first_name",fName);
            params.put("last_name",lName);
            params.put("country",country);
            params.put("city",city);

            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String response1=jsonObject.getString("response");
                        JSONObject jsonObject1=new JSONObject(response1);
                        String access_token=jsonObject1.getString("access_token");
                        city=jsonObject1.getString("city");
                        Log.i("LOG_VOLLEY", access_token+" "+city);
                        saveLoginDetails(email, password,city,access_token);
                        startHomeActivity();
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String message = null;
                    if (error instanceof NetworkError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                        builder.setTitle("Network Error");
                        builder.setMessage("Please check your Internet Connection");
                        displayAlert("false");
                    } else if (error instanceof ServerError) {
                        message = "The server could not be found. Please try again after some time!!";
                        if(password.length()<6)
                        {
                            builder.setTitle("Register Failed");
                            builder.setMessage("Password Lenght must be greater than 6");
                            displayAlert("input_error");
                            Log.i("LOG_VOLLEY", message);
                        }
                        else {
                            builder.setTitle("Register Failed");
                            builder.setMessage("User Already Exists");
                            displayAlert("true");
                            Log.i("LOG_VOLLEY", message);
                        }
                    } else if (error instanceof AuthFailureError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof ParseError) {
                        message = "Parsing error! Please try again after some time!!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof NoConnectionError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof TimeoutError) {
                        message = "Connection TimeOut! Please check your internet connection.";
                        Log.i("LOG_VOLLEY", message);
                        builder.setTitle("Network Error");
                        builder.setMessage("Please check your Internet Connection");
                        displayAlert("false");
                    }

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
                    headers.put("Authorization", "c3ltby1hcHAtaW9zOmE4ODI4NjY1LTU1MzgtNGNlYy1hYzU4LWE0YmU0NmE1Y2Y3OA==");
                    headers.put("Content-Type","application/json");
                    return headers;
                }
            };
            requestQueue.add(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    9000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void displayAlert(final String code)
    {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code.equals("input_error"))
                {
                    Password.setText("");
                    CPassword.setText("");
                }

                if(code.equals("input_error2"))
                {
                    Password.setText("");
                    CPassword.setText("");
                }

                else if(code.equals("false"))
                {

                }

                else if(code.equals("true"))
                {
                    Password.setText("");
                    Email.setText("");
                    CPassword.setText("");
                    FName.setText("");
                    LName.setText("");
                }

            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, BottomTabs.class);
        startActivity(intent);
        finish();
    }

    private void saveLoginDetails(String email, String password,String city,String access_token) {
        new PrefManager(this).saveLoginDetails(email, password,city,access_token);
    }

    public void itemClicked(View v) {
        if (((CheckBox) v).isChecked()) {
            /*Toast.makeText(RegisterFinal.this,
                    "Checked", Toast.LENGTH_LONG).show();*/
            checkbox_result="true";
        }
        else
        {
            //Toast.makeText(RegisterFinal.this,"Not Checked",Toast.LENGTH_LONG).show();
            checkbox_result="false";
        }
    }

    public void RegisterFunc(View v)
    {
        email=Email.getText().toString();
        password=Password.getText().toString();
        cpassword=CPassword.getText().toString();
        fName=FName.getText().toString();
        lName=LName.getText().toString();
        if(email.equals("")||password.equals("")||cpassword.equals("")||fName.equals("")||lName.equals(""))
        {
            builder.setTitle("Something Went Wrong");
            builder.setMessage("Please Fill all the fields");
            displayAlert("input_error");
        }
        if(checkbox_result.equals("false"))
        {
            builder.setTitle("Something Went Wrong");
            builder.setMessage("Please Tick the checkbox");
            displayAlert("false");
        }
        else {
            getData();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        if(spinner.getId() == R.id.jordan)
        {
            country = adapterView.getItemAtPosition(i).toString();
            //Toast.makeText(adapterView.getContext(), "Selected: " + country, Toast.LENGTH_SHORT).show();
            city_url2=city_url+country;
            getData3();
        }
        else if(spinner.getId() == R.id.CitySpinner)
        {
            city = adapterView.getItemAtPosition(i).toString();
            //Toast.makeText(adapterView.getContext(), "Selected: " + city, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getData2()
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
                Toast.makeText(RegisterFinal2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(RegisterFinal2.this).addToRequestque(stringRequest);
    }


    public void getData3()
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
                Toast.makeText(RegisterFinal2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(RegisterFinal2.this).addToRequestque(stringRequest);
    }

}
