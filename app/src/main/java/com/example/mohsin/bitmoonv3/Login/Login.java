package com.example.mohsin.bitmoonv3.Login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class Login extends Activity {
    Button Register;
    Button Login;
    EditText Email;
    EditText Password;
    String email,password;
    AlertDialog.Builder builder;
    String url="http://ngonsolutions.com/bitmoon/login.php";
    String city;
    String country="bbb";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Register=(Button)findViewById(R.id.Register);
        Email=(EditText)findViewById(R.id.email);
        Password=(EditText)findViewById(R.id.password);
        builder=new AlertDialog.Builder(Login.this);
        Login=(Button)findViewById(R.id.SignIn);


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
                                        String user=jsonObject.getString("user");
                                        JSONObject jsonObject1=new JSONObject(user);
                                        city=jsonObject1.getString("city");
                                        if(response1.equals("false"))
                                        {
                                           /* builder.setTitle("Login Success");
                                            builder.setMessage("Welcome");
                                            displayAlert(response1);*/
                                               /* Intent ii=new Intent(getApplicationContext(), BottomTabs.class);
                                                Bundle bundle=new Bundle();
                                                bundle.putString("city",city);
                                                bundle.putString("country",country);
                                                ii.putExtras(bundle);
                                                startActivity(ii);*/
                                           // saveLoginDetails(email, password,city);
                                            startHomeActivity();
                                        }
                                        else
                                        {
                                            builder.setTitle("Login Failed");
                                            builder.setMessage("Try Again");
                                            displayAlert("true");

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params=new HashMap<String, String>();
                            params.put("email",email);
                            params.put("password",password);
                            return params;
                        }
                    };

                    MySingleton.getmInstance(Login.this).addToRequestque(stringRequest);
                }
            }
        });
    }

    /*private void saveLoginDetails(String email, String password,String city) {
        new PrefManager(this).saveLoginDetails(email, password,city);
    }*/

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
