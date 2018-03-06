package com.example.mohsin.bitmoonv3.FoodDrink;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.mohsin.bitmoonv3.Attractions.AttractionActivity;
import com.example.mohsin.bitmoonv3.BeautyFitness.BeautyActivity;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.Hotels.HotelActivity;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.Retails.RetailActivity;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.VendorFavourites.FavAdapter;
import com.example.mohsin.bitmoonv3.VendorFavourites.FavouriteActivity;
import com.example.mohsin.bitmoonv3.models.Fav_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDetail2 extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int VendorIdNew,favourite;
    String token,vendor_name,VendorId,LanGet;
    ImageView FavNot,Fav,back;
    TextView ResName;
    String activity_name;
    private List<Integer> list=new ArrayList<Integer>();
    String Base_Url;
    String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail2);

        initView2();
        preferences();
        Base_Url=getString(R.string.Base_Url);
        get_all_favourite();

        ResName.setText(vendor_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(LanGet.equals("en"))
        {
            tabLayout.addTab(tabLayout.newTab().setText("Offers"));
            tabLayout.addTab(tabLayout.newTab().setText("Details"));
        }
        else if(LanGet.equals("ar"))
        {
            tabLayout.addTab(tabLayout.newTab().setText(R.string.offers_ar));
            tabLayout.addTab(tabLayout.newTab().setText(R.string.details_ar));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        FoodPager2 adapter = new FoodPager2(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity_name.equals("Food"))
                {
                    PrefManager prefManager=new PrefManager(getApplicationContext());
                    prefManager.setBranchId(null);
                    Intent i = new Intent(FoodDetail2.this, FoodActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
                else if(activity_name.equals("Home"))
                {
                    PrefManager prefManager=new PrefManager(getApplicationContext());
                    prefManager.setBranchId(null);
                    Intent i=new Intent(FoodDetail2.this, BottomTabs.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
                else if(activity_name.equals("Favourite"))
                {
                    PrefManager prefManager=new PrefManager(getApplicationContext());
                    prefManager.setBranchId(null);
                    Intent i=new Intent(FoodDetail2.this, FavouriteActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("name","Favourite");
                    i.putExtras(bundle);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
                else if(activity_name.equals("Beauty"))
                {
                    PrefManager prefManager=new PrefManager(getApplicationContext());
                    prefManager.setBranchId(null);
                    Intent i = new Intent(FoodDetail2.this, BeautyActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
                else if(activity_name.equals("Attraction"))
                {
                    PrefManager prefManager=new PrefManager(getApplicationContext());
                    prefManager.setBranchId(null);
                    Intent i = new Intent(FoodDetail2.this, AttractionActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
                else if(activity_name.equals("Retail"))
                {
                    PrefManager prefManager=new PrefManager(getApplicationContext());
                    prefManager.setBranchId(null);
                    Intent i = new Intent(FoodDetail2.this, RetailActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
                else if(activity_name.equals("Hotel"))
                {
                    PrefManager prefManager=new PrefManager(getApplicationContext());
                    prefManager.setBranchId(null);
                    Intent i = new Intent(FoodDetail2.this, HotelActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
                else if(activity_name.equals("Notification"))
                {
                    PrefManager prefManager=new PrefManager(getApplicationContext());
                    prefManager.setBranchId(null);
                    Intent i=new Intent(FoodDetail2.this, BottomTabs.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
            }
        });

        /*new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        PrefManager aa=new PrefManager(getApplicationContext());
                        VendorId=aa.getvendorId().toString();
                        VendorIdNew=Integer.parseInt(VendorId);
                        Log.i("Response1", String.valueOf(VendorIdNew));
                    }
                },
                1000
        );*/
        PrefManager aa=new PrefManager(getApplicationContext());
        VendorId=aa.getvendorId().toString();
        VendorIdNew=Integer.parseInt(VendorId);
        Log.i("Response1", String.valueOf(VendorIdNew));

        Fav.setVisibility(View.GONE);
        FavNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavNot.setVisibility(View.GONE);
                Fav.setVisibility(View.VISIBLE);
                favourite=1;
                getFavourite();
            }
        });
        Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fav.setVisibility(View.GONE);
                FavNot.setVisibility(View.VISIBLE);
                favourite=0;
                getFavourite();
            }
        });
    }

    public void getFavourite(){
        String url2=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor/favourite";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("vendor_id",VendorIdNew);
            params.put("favourite",favourite);

            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response",response.toString());
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String status=jsonObject.getString("status");
                        Log.i("Response", status.toString());
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

    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void onBackPressed()
    {
        if(activity_name.equals("Food"))
        {
            PrefManager prefManager=new PrefManager(getApplicationContext());
            prefManager.setBranchId(null);
            Intent i = new Intent(FoodDetail2.this, FoodActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
        else if(activity_name.equals("Home"))
        {
            PrefManager prefManager=new PrefManager(getApplicationContext());
            prefManager.setBranchId(null);
            Intent i=new Intent(FoodDetail2.this, BottomTabs.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
        else if(activity_name.equals("Favourite"))
        {
            PrefManager prefManager=new PrefManager(getApplicationContext());
            prefManager.setBranchId(null);
            Intent i=new Intent(FoodDetail2.this, BottomTabs.class);
            Bundle bundle=new Bundle();
            bundle.putString("name","Favourite");
            i.putExtras(bundle);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
        else if(activity_name.equals("Beauty"))
        {
            PrefManager prefManager=new PrefManager(getApplicationContext());
            prefManager.setBranchId(null);
            Intent i = new Intent(FoodDetail2.this, BeautyActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
        else if(activity_name.equals("Attraction"))
        {
            PrefManager prefManager=new PrefManager(getApplicationContext());
            prefManager.setBranchId(null);
            Intent i = new Intent(FoodDetail2.this, AttractionActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
        else if(activity_name.equals("Retail"))
        {
            PrefManager prefManager=new PrefManager(getApplicationContext());
            prefManager.setBranchId(null);
            Intent i = new Intent(FoodDetail2.this, RetailActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
        else if(activity_name.equals("Hotel"))
        {
            PrefManager prefManager=new PrefManager(getApplicationContext());
            prefManager.setBranchId(null);
            Intent i = new Intent(FoodDetail2.this, HotelActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }
        else if(activity_name.equals("Notification"))
        {
            PrefManager prefManager=new PrefManager(getApplicationContext());
            prefManager.setBranchId(null);
            Intent i=new Intent(FoodDetail2.this, BottomTabs.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            finish();
        }


    }


    public void get_all_favourite()
    {
        String url=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor/favourite?lang=en";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.i("Response","Not Null");
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                Fav_Model fav_model=new Fav_Model();
                                JSONObject actor = jsonArray.getJSONObject(i);
                                fav_model.setVendorId(actor.getString("vendor_id"));
                                fav_model.setVendorName(actor.getString("vendor_name"));
                                fav_model.setLat(actor.getString("lat"));
                                fav_model.setLng(actor.getString("long"));
                                fav_model.setAvatar(actor.getString("vendor_avatar"));
                                int vendor_id= Integer.parseInt(actor.getString("vendor_id"));
                                list.add(vendor_id);
                                Log.i("Response3",list.toString());
                                Star();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FoodDetail2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(FoodDetail2.this).addToRequestque(stringRequest);
    }

    public void initView2()
    {
        ResName=(TextView)findViewById(R.id.FoodText);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        back=(ImageView)findViewById(R.id.back);
        Fav=(ImageView)findViewById(R.id.fav);
        FavNot=(ImageView)findViewById(R.id.fav_not);

        Bundle bundle=new Bundle();
        bundle=getIntent().getExtras();
        activity_name=bundle.getString("Activity");
    }

    public void preferences()
    {
        PrefManager aa=new PrefManager(getApplicationContext());
        token=aa.getToken().toString();
        vendor_name=aa.getvendorName().toString();
        LanGet=aa.getLanguage();
        aa.setActivity_1(activity_name);
        Log.i("Shared",aa.getTypeArray());
        Log.i("Shared",aa.getCuisineArray());
        Log.i("Shared",aa.getAdditionalArray());
    }


    public void Star()
    {
        Log.d("Match", String.valueOf(VendorIdNew));
        for(int i=0;i<list.size();i++)
        {
            if(VendorIdNew== list.get(i))
            {
                Log.i("Match","1");
                Fav.setVisibility(View.VISIBLE);
            }
            else if(VendorIdNew!=list.get(i))
            {
                Log.i("Match","2");
            }
        }
    }
}
