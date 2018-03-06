package com.example.mohsin.bitmoonv3.CustomerProfile;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.Login.NewTest;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.CurrencyModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.R.layout.simple_spinner_item;


public class Info2 extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String item="Choose Your Nationality";
    TextView Country;
    String token;

    private EditText Date;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    CurrencyAdapter adapter = null;
    ArrayList<CurrencyModel> currency = null;
    ArrayList<CurrencyModel> currency1 = null;
    //Spinner mySpinner ;
    Switch email_switch,notification_switch;
    int bitmoon_email=0;
    int notification=0;
    TextView Update;
    String date_new;
    String country_id,currency_id;
    AlertDialog.Builder builder;
    String Base_Url,LanGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefManager prefManager2=new PrefManager(getApplicationContext());
        LanGet=prefManager2.getLanguage();
        if(LanGet.equals("en"))
        {
            setContentView(R.layout.activity_information);
        }
        else if(LanGet.equals("ar"))
        {
            setContentView(R.layout.activity_information2);
        }
        Base_Url=getString(R.string.Base_Url);

        final Spinner mySpinner = (Spinner) findViewById( R.id.spinner_currency) ;
        final Spinner mySpinner1 = (Spinner) findViewById( R.id.spinner_country) ;

        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();

        email_switch=(Switch)findViewById(R.id.switch1);
        email_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked) {
                    bitmoon_email=1;
                    //Toast.makeText(getApplicationContext(),bitmoon_email,Toast.LENGTH_LONG).show();
                }
                else
                {
                    bitmoon_email=0;
                    //Toast.makeText(getApplicationContext(),bitmoon_email,Toast.LENGTH_LONG).show();
                }
            }
        });


        notification_switch=(Switch)findViewById(R.id.switch2);
        notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked) {
                    notification=1;
                }
                else
                {
                    notification=0;
                }
            }
        });

        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), CustomerProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }
        });

        TextView update=(TextView)findViewById(R.id.MapText);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), CustomerProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }
        });

        Date = (EditText) findViewById(R.id.date_txt);
        Date.setInputType(InputType.TYPE_NULL);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setDateTimeField();



        currency1 = new ArrayList<>();
        currency1 = populateCustomerData1(currency1);
        adapter = new CurrencyAdapter(this, currency1);
        mySpinner1.setAdapter(adapter);
        mySpinner1.setOnItemSelectedListener(this);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(this);

        /*currency = new ArrayList<>();
        currency = populateCustomerData(currency);
        adapter = new CurrencyAdapter(this, currency);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(this);*/

        Update=(TextView)findViewById(R.id.MapText);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAlert();
                //Update_Profile();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        country_id = parent.getItemAtPosition(position).toString();

        currency_id = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void setDateTimeField() {
        Date.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this,R.style.MySpinnerDatePickerStyle,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date_new=dateFormatter.format(newDate.getTime());
                //Date.setText(dateFormatter.format(newDate.getTime()));
                Date.setText(date_new);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        toDatePickerDialog.show();
    }


    public void onBackPressed()
    {
        Intent i = new Intent(Info2.this, CustomerProfileActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
        finish();
    }

    private ArrayList<CurrencyModel> populateCustomerData(final ArrayList<CurrencyModel> currency) {
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
                                currency.add(currencyModel);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Info2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization", token);
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        };
        MySingleton.getmInstance(Info2.this).addToRequestque(stringRequest);

        /*currency.add(new CurrencyModel("", 1, R.drawable.jordan));
        currency.add(new CurrencyModel("", 2, R.drawable.lebanon));
        currency.add(new CurrencyModel("", 3, R.drawable.egypt));
        currency.add(new CurrencyModel("", 8, R.drawable.uae));*/
        return currency;
    }

    private ArrayList<CurrencyModel> populateCustomerData1(final ArrayList<CurrencyModel> currency1) {
        String url=Base_Url+"/bitmoonbackend/public/index.php/api/user/countries?lang=en";
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
                Toast.makeText(Info2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization", token);
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        };
        MySingleton.getmInstance(Info2.this).addToRequestque(stringRequest);
        /*currency1.add(new CurrencyModel("", 1, R.drawable.jordan));
        currency1.add(new CurrencyModel("", 2, R.drawable.lebanon));
        currency1.add(new CurrencyModel("", 3, R.drawable.egypt));
        currency1.add(new CurrencyModel("", 8, R.drawable.uae));*/
        return currency1;
    }


    public void Update_Profile(){
        String url2=Base_Url+"/bitmoonbackend/public/index.php/api/user/profile/update";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("date_of_birth",date_new);
            params.put("country",country_id);
            params.put("phone","1111");
            params.put("currency",currency_id);
            params.put("bitmoon_email",bitmoon_email);
            params.put("notification",notification);


            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("ResponseUpdate",response);
                    Toast.makeText(getApplicationContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(getApplicationContext(),CustomerProfileActivity.class);
                    startActivity(i);
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

    public void displayAlert()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Update_Profile();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(Info2.this);
        builder.setTitle("Update Profile");
        builder.setMessage("Are you sure you want to Update Profile?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

}

