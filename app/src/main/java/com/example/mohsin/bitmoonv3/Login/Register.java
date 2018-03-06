package com.example.mohsin.bitmoonv3.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Register extends Activity implements AdapterView.OnItemSelectedListener{
    Spinner spinnerCountry, spinnerCity;
    ImageView Register;
    String city;
    String country;
    EditText Email,Password,CPassword,FName,LName;
    String email,password,cpassword,fName,lName;
    AlertDialog.Builder builder;
    String url="http://ngonsolutions.com/bitmoon/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email=(EditText)findViewById(R.id.Email);
        Password=(EditText)findViewById(R.id.Password);
        CPassword=(EditText)findViewById(R.id.CPassword);
        FName=(EditText)findViewById(R.id.FName);
        LName=(EditText)findViewById(R.id.LName);
        builder=new AlertDialog.Builder(Register.this);


        spinnerCountry = (Spinner) findViewById(R.id.jordan);
        spinnerCity = (Spinner) findViewById(R.id.CitySpinner);
        Register=(ImageView) findViewById(R.id.register);
        spinnerCountry.setOnItemSelectedListener(this);

        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                parentView.getItemAtPosition(position);
                city=spinnerCity.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
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
                else
                {
                    StringRequest stringRequest =new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        //JSONArray jsonArray=new JSONArray(response);
                                        //JSONObject jsonObject=jsonArray.getJSONObject(0);
                                        JSONObject jsonObject = new JSONObject(response);
                                        String response1=jsonObject.getString("error");
                                        String message="Try Again";
                                        if(response1.equals("false"))
                                        {
                                            /*builder.setTitle("Register Success");
                                            builder.setMessage("Welcome");
                                            displayAlert(response1);
                                            Intent ii=new Intent(getApplicationContext(), BottomTabs.class);
                                            Bundle bundle=new Bundle();
                                            bundle.putString("city",city);
                                            bundle.putString("country",country);
                                            ii.putExtras(bundle);
                                            startActivity(ii);*/
                                            //saveLoginDetails(email, password,city);
                                            startHomeActivity();
                                        }
                                        else if(response1.equals("true"))
                                        {
                                            builder.setTitle("Register Failed");
                                            builder.setMessage("User Aleardy Exists");
                                            displayAlert(response1);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Register.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<String, String>();
                            params.put("firstname",fName);
                            params.put("lastname",lName);
                            params.put("email",email);
                            params.put("password",password);
                            params.put("city",city);
                            params.put("country",country);
                            return params;
                        }
                    };

                    MySingleton.getmInstance(Register.this).addToRequestque(stringRequest);

                }
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
                               long arg3) {
        parent.getItemAtPosition(pos);
        if (pos == 0) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_jordan,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
            country=spinnerCountry.getSelectedItem().toString();
        }
        else if (pos == 1) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_lebnan,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
            country=spinnerCountry.getSelectedItem().toString();
        }
        else if (pos == 2) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_egypt,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
            country=spinnerCountry.getSelectedItem().toString();
        }
        else if (pos == 3) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter
                    .createFromResource(this, R.array.city_uae,
                            android.R.layout.simple_spinner_item);
            spinnerCity.setAdapter(adapter);
            country=spinnerCountry.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

  /*  private void saveLoginDetails(String email, String password,String city) {
        new PrefManager(this).saveLoginDetails(email, password,city);
    }*/

    private void startHomeActivity() {
        Intent intent = new Intent(this, com.example.mohsin.bitmoonv3.Home.HomeActivity.class);
        startActivity(intent);
        finish();
    }


}
