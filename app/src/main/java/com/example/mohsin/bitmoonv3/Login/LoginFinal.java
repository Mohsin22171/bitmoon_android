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
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginFinal extends Activity{
    Button Register;
    Button Login;
    EditText Email;
    EditText Password;
    String email,password;
    AlertDialog.Builder builder;
    String Base_Url;
    String url;
    String city;
    String country="bbb";
    String LanGet="en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Base_Url=getString(R.string.Base_Url);
        url=Base_Url+"/bitmoonbackend/public/index.php/api/user/login";
        Register=(Button)findViewById(R.id.Register);
        Email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.password);
        builder=new AlertDialog.Builder(LoginFinal.this);
        Login=(Button)findViewById(R.id.SignIn);

        PrefManager prefManager=new PrefManager(getApplicationContext());
        prefManager.setLanguage(LanGet);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),RegisterFinal.class);
                startActivity(i);
            }
        });
        if (!new PrefManager(this).isUserLogedOut()) {
            //user's email and password both are saved in preferences
            startHomeActivity();
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=Email.getText().toString();
                password=Password.getText().toString();
                if(email.equals("")||password.equals(""))
                {
                    builder.setTitle("Something Went Wrong");
                    builder.setMessage("Please Fill all the fields");
                    displayAlert("input_error");
                }
                else {
                    getData();
                }
            }
        });
    }


    public void getData(){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("email",email);
            params.put("password",password);

            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response!=null) {
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        Log.i("LOG_VOLLEY", "aaa");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.e("LOG_VOLLEY", error.toString());
                    /*String message = null;
                    if (error instanceof NetworkError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                        builder.setTitle("Network Error");
                        builder.setMessage("Please check your Internet Connection");
                        displayAlert("false");
                    } else if (error instanceof ServerError) {
                        message = "The server could not be found. Please try again after some time!!";
                        builder.setTitle("Login Failed");
                        builder.setMessage("Try Again");
                        displayAlert("true");
                        Log.i("LOG_VOLLEY", message);
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
                    }*/

                    Log.e("LOG_VOLLEY", error.toString());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        try {
                            JSONObject jsonObject = new JSONObject(jsonError);
                            JSONObject error1 = new JSONObject(jsonObject.getString("error"));
                            Log.i("UserResponse2", error1.toString());
                            String custom_code;
                            custom_code = error1.getString("custom_code");
                            if (custom_code.equals("502")) {
                                builder.setTitle("Login Failed");
                                builder.setMessage("You are blocked by the Super Admin.");
                                displayAlert("true");
                            }
                            if (custom_code.equals("404")) {
                                builder.setTitle("Login Failed");
                                builder.setMessage("Wrong Email or Password Entered!");
                                displayAlert("true");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveLoginDetails(String email, String password,String city,String access_token) {
        new PrefManager(this).saveLoginDetails(email, password,city,access_token);
    }

    public void displayAlert(final String code)
    {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code.equals("input_error"))
                {
                    Email.setText("");
                    Password.setText("");
                }

                else if(code.equals("false"))
                {

                }

                else if(code.equals("true"))
                {
                    Password.setText("");
                    Email.setText("");
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

}
