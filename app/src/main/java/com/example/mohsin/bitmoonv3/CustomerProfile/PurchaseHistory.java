package com.example.mohsin.bitmoonv3.CustomerProfile;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodDetail2;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.VendorFavourites.FavAdapter;
import com.example.mohsin.bitmoonv3.models.Fav_Model;
import com.example.mohsin.bitmoonv3.models.Purchase_History_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;

public class PurchaseHistory extends Activity{
    private List<Purchase_History_Model> list=new ArrayList<Purchase_History_Model>();
    private ListView listView;
    private Purchase_History_Adapter favAdapter;
    Context context=this;
    String token;
    private ProgressBar progressBar;
    TextView result;
    String Base_Url;
    String LanGet;
    RelativeLayout relativeLayout;
    TextView HeaderTxt;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        initView2();
        preferences();
        Base_Url=getString(R.string.Base_Url);
        result.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getFavourite();

        if(LanGet.equals("en"))
        {
            relativeLayout.setLayoutDirection(LTR);
            HeaderTxt.setText("Purchase History");
        }
        else if(LanGet.equals("ar"))
        {
            relativeLayout.setLayoutDirection(RTL);
            HeaderTxt.setText("تاريخ شراء");
        }

    }

    public void getFavourite()
    {
        String url=Base_Url+"/bitmoonbackend/public/index.php/api/user/purchase_history?lang="+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response",response.toString());
                        favAdapter= new Purchase_History_Adapter(PurchaseHistory.this,list);
                        listView.setAdapter(favAdapter);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            if(response1.equals("[]"))
                            {
                                Log.i("Response","Null");
                                result.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                Log.i("Response","Not Null");
                                JSONArray jsonArray=new JSONArray(response1);
                                result.setVisibility(View.GONE);
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    Purchase_History_Model fav_model=new Purchase_History_Model();
                                    JSONObject actor = jsonArray.getJSONObject(i);
                                    fav_model.setOffer_id(actor.getString("offer_id"));
                                    fav_model.setOffer_name(actor.getString("offer_name"));
                                    fav_model.setOffer_detail(actor.getString("offer_details"));
                                    list.add(fav_model);
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PurchaseHistory.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(PurchaseHistory.this).addToRequestque(stringRequest);
    }

    public void initView2()
    {
        listView=(ListView)findViewById(R.id.Purchase_list);
        result=(TextView)findViewById(R.id.result);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        relativeLayout=(RelativeLayout)findViewById(R.id.purchase_main);
        HeaderTxt=(TextView)findViewById(R.id.FoodText);
    }

    public void preferences()
    {
        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();
        LanGet=prefManager.getLanguage();
    }

    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(), CustomerProfileActivity.class);
        startActivity(i);
    }
}
