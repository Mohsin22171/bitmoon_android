package com.example.mohsin.bitmoonv3.CustomerProfile;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohsin.bitmoonv3.Login.RegisterFinal;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class PinActivity extends Activity {
    String a,b,c,d,pin;
    ImageView back;
    String token;
    TextView save,Header,BodyText;
    String PinBundle;
    String Base_Url,LanGet;
    TextView HeaderTxt,BodyTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        initView2();
        preferences();
        Base_Url=getString(R.string.Base_Url);

        if(LanGet.equals("en"))
        {
            HeaderTxt.setText("Create PIN");
            BodyText.setText("Please Create a PIN. You will use it to securely redeem offers");
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null && bundle.getString("Pin")!=null)
            {
                PinBundle=bundle.getString("Pin");
                Header.setText("Reset PIN");
                BodyText.setText("Please Reset a PIN. You will use it to securely redeem offers");
            }
            else
            {
                Log.i("Pin","bbb");
            }
        }
        else if(LanGet.equals("ar"))
        {
            HeaderTxt.setText("إنشاء دبوس");
            BodyText.setText("الرجاء إنشاء رقم تعريف شخصي. سوف تستخدمه لتخليص العروض بشكل آمن");
            Bundle bundle=getIntent().getExtras();
            if(bundle!=null && bundle.getString("Pin")!=null)
            {
                PinBundle=bundle.getString("Pin");
                Header.setText("إعادة تعيين دبوس");
                BodyText.setText("يرجى إعادة تعيين رقم التعريف الشخصي. سوف تستخدمه لتخليص العروض بشكل آمن");
            }
            else
            {
                Log.i("Pin","bbb");
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PinBundle!=null)
                {
                    Intent i=new Intent(getApplicationContext(), CustomerProfileActivity2.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("name","Profile");
                    i.putExtras(bundle);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
                else
                {
                    Intent i=new Intent(getApplicationContext(), CustomerProfileActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("name","Profile");
                    i.putExtras(bundle);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
            }
        });
        save.setVisibility(View.GONE);

        final EditText edtxt1 = (EditText)findViewById(R.id.digit1);
        final EditText edtxt2 = (EditText)findViewById(R.id.digit2);
        final EditText edtxt3 = (EditText)findViewById(R.id.digit3);
        final EditText edtxt4 = (EditText)findViewById(R.id.digit4);

        edtxt1.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() ==1) {
                    edtxt2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                a=edtxt1.getText().toString();
            }
        });

        edtxt1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    edtxt1.getText().clear();
                }
                return false;
            }
        });


        edtxt2.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    edtxt3.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                b=edtxt2.getText().toString();
            }
        });

        edtxt2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    edtxt1.requestFocus();
                }
                return false;
            }
        });

        edtxt3.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    edtxt4.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                c=edtxt3.getText().toString();

            }
        });

        edtxt3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    //edtxt3.getText().clear();
                    edtxt2.requestFocus();
                }
                return false;
            }
        });

        edtxt4.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                d=edtxt4.getText().toString();
                pin=a+b+c+d;
                save.setVisibility(View.VISIBLE);
            }
        });

        edtxt4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    //edtxt4.getText().clear();
                    edtxt3.requestFocus();
                }
                return false;
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPin();
                /*PrefManager prefManager=new PrefManager(getApplicationContext());
                prefManager.setUser_Pin(pin);*/
            }
        });

    }

    public void onBackPressed()
    {
        if(PinBundle!=null)
        {
            Intent i = new Intent(PinActivity.this, CustomerProfileActivity2.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
        else
        {
            Intent i = new Intent(PinActivity.this, CustomerProfileActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
    }



    public void getPin(){
        String url2=Base_Url+"/bitmoonbackend/public/index.php/api/user/secret_pin";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("secret_pin",pin);

            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String status=jsonObject.getString("status");
                        if(status.equals("true"))
                        {
                            Log.i("Response","True");
                           Toast.makeText(getApplicationContext(),"Pin Created Successfully",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),CustomerProfileActivity.class);
                            startActivity(i);
                        }
                        else if(status.equals("false"))
                        {
                            Log.i("Response","False");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                    headers.put("Authorization", token);
                    headers.put("Content-Type","application/json");
                    return headers;
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void initView2()
    {
        back=(ImageView)findViewById(R.id.back);
        save=(TextView)findViewById(R.id.Save);
        Header=(TextView)findViewById(R.id.FoodText);
        BodyText=(TextView)findViewById(R.id.text);
        HeaderTxt=(TextView)findViewById(R.id.FoodText);
        BodyTxt=(TextView)findViewById(R.id.text);
    }

    public void preferences()
    {
        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();
        LanGet=prefManager.getLanguage();
    }
}
