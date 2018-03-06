package com.example.mohsin.bitmoonv3.CustomerProfile;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodActivity;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodAdapter2;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.RuleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleOfUse extends Activity {
    private ListView RuleListView;
    private List<RuleModel>rule_list=new ArrayList<RuleModel>();
    private RuleAdapter rule_adapter;
    String token;
    ImageView cross;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();
        getRule();

        final Dialog rule_dialog = new Dialog(RuleOfUse.this,R.style.FullDialog);
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

        rule_dialog.show();


        RuleListView=(ListView)rule_dialog.findViewById(R.id.ListView);
    }

    public void getRule()
    {
        String profile_url="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/rules?lang=en";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, profile_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.d("Response",response1);
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                rule_adapter = new RuleAdapter(RuleOfUse.this, rule_list);
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
                Toast.makeText(RuleOfUse.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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

        MySingleton.getmInstance(RuleOfUse.this).addToRequestque(stringRequest);
    }

}
