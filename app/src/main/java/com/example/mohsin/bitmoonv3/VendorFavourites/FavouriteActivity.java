package com.example.mohsin.bitmoonv3.VendorFavourites;


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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodDetail2;
import com.example.mohsin.bitmoonv3.Home.HomeRecyclerAdapter;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Fav_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteActivity extends Activity{
    private List<Fav_Model> list=new ArrayList<Fav_Model>();
    private ListView listView;
    private FavAdapter favAdapter;
    Context context=this;
    String token;
    TextView result;
    private ProgressBar progressBar;
    String Base_Url;
    String LanGet;
    TextView HeaderTxt;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        initView2();
        preferences();
        Base_Url=getString(R.string.Base_Url);
        result.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getFavourite();

        if(LanGet.equals("en"))
        {
            HeaderTxt.setText("Favourites");
        }
        else if(LanGet.equals("ar"))
        {
            HeaderTxt.setText("المفضلة");
        }

    }

    public void getFavourite()
    {
        String url=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor/favourite?lang="+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response",response.toString());
                        favAdapter= new FavAdapter(FavouriteActivity.this,list);
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
                                    Fav_Model fav_model=new Fav_Model();
                                    JSONObject actor = jsonArray.getJSONObject(i);
                                    fav_model.setVendorId(actor.getString("vendor_id"));
                                    fav_model.setVendorName(actor.getString("vendor_name"));
                                    fav_model.setLat(actor.getString("lat"));
                                    fav_model.setLng(actor.getString("long"));
                                    fav_model.setAvatar(actor.getString("vendor_avatar"));
                                    fav_model.setAddress(actor.getString("address"));
                                    list.add(fav_model);
                                    String name = actor.getString("vendor_name");
                                    Log.i("IO",name);
                                    PrefManager prefManager=new PrefManager(getApplicationContext());
                                    prefManager.setVendorId(actor.getString("vendor_id"));
                                }
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        String vendor_id=list.get(position).getVendorId();
                                        String vendor_name=list.get(position).getVendorName();
                                        String Lat=list.get(position).getLat();
                                        String Lng=list.get(position).getLng();
                                        PrefManager aa = new PrefManager(getApplicationContext());
                                        aa.setVendorId(vendor_id);
                                        aa.setVendorName(vendor_name);
                                        aa.setLat(Lat);
                                        aa.setLng(Lng);
                                        Intent i=new Intent(getApplicationContext(),FoodDetail2.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putString("Activity","Favourite");
                                        i.putExtras(bundle);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                    }
                                });

                            }
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FavouriteActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(FavouriteActivity.this).addToRequestque(stringRequest);
    }

    public void initView2()
    {
        listView=(ListView)findViewById(R.id.fav_list);
        result=(TextView)findViewById(R.id.result);
        progressBar = (ProgressBar)findViewById(R.id.progress);
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
        Intent i=new Intent(getApplicationContext(), BottomTabs.class);
        startActivity(i);
    }
}
