package com.example.mohsin.bitmoonv3.CustomerProfile;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.InstructionsWalkThrough.ViewPagerStyle1Activity;
import com.example.mohsin.bitmoonv3.Login.Login;
import com.example.mohsin.bitmoonv3.Login.LoginFinal;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.Login.NewTest;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Profile_Account_Model;
import com.example.mohsin.bitmoonv3.models.RuleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerProfileActivity2 extends Activity{
    String ProfileItem[],token;
    private ProfileAdapter profileAdapter;
    private List<Profile_Account_Model>list=new ArrayList<>();
    TextView Signout;
    ListView ProfileList;
    ImageView back;
    String secret_key;
    final String key_check="";
    private ListView RuleListView;
    private List<RuleModel>rule_list=new ArrayList<RuleModel>();
    private RuleAdapter rule_adapter;
    ImageView cross;
    AlertDialog.Builder builder;
    String Base_Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        initView2();
        preferences();
        Base_Url=getString(R.string.Base_Url);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), BottomTabs.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","Profile");
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAlert();
                //getSignOut();
            }
        });

        ProfileItem =new String[]
                {
                        "My Information","Reset PIN","Instructions","Purchase History","Reset Password","End User License Aggrement","Rules of Use","Hotel Rules of Use"
                };

        for(int i=0;i<ProfileItem.length;i++)
        {
            Profile_Account_Model aa=new Profile_Account_Model(ProfileItem[i]);
            list.add(aa);
        }
        profileAdapter=new ProfileAdapter(this,list);
        ProfileList.setAdapter(profileAdapter);

        ProfileList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Intent i=new Intent(getApplicationContext(),Info2.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
                else if(position==1)
                {
                    Intent i=new Intent(getApplicationContext(),PinActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Pin","Reset");
                    i.putExtras(bundle);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
                else if(position==2)
                {
                    Intent i=new Intent(getApplicationContext(), ViewPagerStyle1Activity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                }
                else if(position==3)
                {
                    Intent i=new Intent(getApplicationContext(), PurchaseHistory.class);
                    startActivity(i);
                }
                else if(position==6)
                {
                    //Toast.makeText(getApplicationContext(),"6",Toast.LENGTH_SHORT).show();
                    getRule();
                    final Dialog rule_dialog = new Dialog(CustomerProfileActivity2.this,R.style.FullDialog);
                    rule_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    rule_dialog.setContentView(R.layout.activity_rule);
                    rule_dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

                    cross=(ImageView)rule_dialog.findViewById(R.id.cross);
                    cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rule_dialog.dismiss();
                        }
                    });
                    RuleListView=(ListView)rule_dialog.findViewById(R.id.ListView);
                    rule_dialog.show();

                }
                else if(position==7)
                {
                    getRule();
                    final Dialog rule_dialog = new Dialog(CustomerProfileActivity2.this,R.style.FullDialog);
                    rule_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    rule_dialog.setContentView(R.layout.activity_rule);
                    rule_dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);

                    cross=(ImageView)rule_dialog.findViewById(R.id.cross);
                    cross.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rule_dialog.dismiss();
                        }
                    });
                    RuleListView=(ListView)rule_dialog.findViewById(R.id.ListView);
                    rule_dialog.show();
                }
                else {
                    Toast.makeText(CustomerProfileActivity2.this, "3", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(), BottomTabs.class);
        Bundle bundle=new Bundle();
        bundle.putString("name","Profile");
        i.putExtras(bundle);
        startActivity(i);
    }


    public void getSignOut()
    {
        String sign_out_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/logout";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sign_out_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("status");
                            Log.i("Response",response1.toString());
                            if(response1.equals("true"))
                            {
                                saveLoginDetails(null,null,null,null);
                                Intent i=new Intent(getApplicationContext(), LoginFinal.class);
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CustomerProfileActivity2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(CustomerProfileActivity2.this).addToRequestque(stringRequest);
    }

    private void saveLoginDetails(String email, String password,String city,String access_token) {
        new PrefManager(this).saveLoginDetails(email, password,city,access_token);
    }


    public void getRule()
    {
        String profile_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/rules?lang=en";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, profile_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            rule_list.clear();
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.d("Response",response1);
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                rule_adapter = new RuleAdapter(CustomerProfileActivity2.this, rule_list);
                                RuleListView.setAdapter(rule_adapter);
                                RuleModel ruleModel=new RuleModel();
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String rule_id = actor.getString("id");
                                String description=actor.getString("description");
                                ruleModel.setRule_id(rule_id);
                                ruleModel.setDescription(description);
                                rule_list.add(ruleModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CustomerProfileActivity2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(CustomerProfileActivity2.this).addToRequestque(stringRequest);
    }


    public void initView2()
    {
        back=(ImageView)findViewById(R.id.back);
        ProfileList=(ListView)findViewById(R.id.profile_list);
        Signout=(TextView)findViewById(R.id.signout);
    }

    public void preferences()
    {
        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();
    }

    public void displayAlert()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        getSignOut();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(CustomerProfileActivity2.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to Logout?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
