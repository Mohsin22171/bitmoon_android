package com.example.mohsin.bitmoonv3.Location;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
import com.example.mohsin.bitmoonv3.FoodDrink.FoodDetail2;
import com.example.mohsin.bitmoonv3.FoodDrink.TypeAdapter;
import com.example.mohsin.bitmoonv3.Home.Home3;
import com.example.mohsin.bitmoonv3.Home.HomeRecyclerAdapter;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.Login.NewTest;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Data_Model;
import com.example.mohsin.bitmoonv3.models.LocationModel;
import com.example.mohsin.bitmoonv3.models.TypeModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mohsin.bitmoonv3.R.id.view;

public class Loc2 extends Activity{
    List<String> country_list = new ArrayList<String>();
    List<String>country_flag=new ArrayList<String>();
    int position=0,size;
    Context context=this;
    String url3;
    String city="aaa",token,city_id="none";
    private ProgressBar progressBar;
    TextView city_text,country_text,loc_txt,city_label;
    Button Done;
    ImageView next,prev,flag;
    String LanGet;

    private List<LocationModel> list = new ArrayList<LocationModel>();
    private ListView listView;
    private LocationAdapter adapter;
    String[] city_name;
    String Base_Url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location);
        initView2();
        preferences();
        Base_Url=getString(R.string.Base_Url);

        progressBar.setVisibility(View.VISIBLE);
        getLocation();   // Country Webservice //
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(city_id.equals("none"))
                {
                    Intent ii = new Intent(getApplicationContext(), BottomTabs.class);
                    startActivity(ii);
                }
                else
                {
                    getCityUpdate();  //City Update Webservice //
                }
            }
        });
    }

    // Country Webservice //
    public void getLocation()
    {
        String country_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/countries?lang=";
        String country_urlA=country_url+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, country_urlA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String country_name = actor.getString("country_name");
                                String flag=actor.getString("country_flag");
                                country_list.add(country_name);
                                country_flag.add(flag);
                            }
                            size=country_list.size()-1;
                            getCity();

                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    list.clear();
                                    position=position+1;
                                    progressBar.setVisibility(View.VISIBLE);
                                    getCity();  // City Webservice //
                                    if(position==size)
                                    {
                                        next.setVisibility(view.GONE);
                                        country_text.setText(country_list.get(position));
                                        Picasso.with(context).load(country_flag.get(position)).into(flag);
                                        prev.setVisibility(view.VISIBLE);
                                    }
                                    else
                                    {
                                        next.setVisibility(view.VISIBLE);
                                        country_text.setText(country_list.get(position));
                                        Picasso.with(context).load(country_flag.get(position)).into(flag);
                                        prev.setVisibility(view.VISIBLE);
                                    }
                                }
                            });
                            country_text.setText(country_list.get(position));

                            prev.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    list.clear();
                                    position=position-1;
                                    progressBar.setVisibility(View.VISIBLE);
                                    getCity();  // City Webservice //
                                    if(position==0)
                                    {
                                        prev.setVisibility(view.GONE);
                                        country_text.setText(country_list.get(position));
                                        Picasso.with(context).load(country_flag.get(position)).into(flag);
                                        next.setVisibility(view.VISIBLE);
                                    }
                                    else
                                    {
                                        country_text.setText(country_list.get(position));
                                        Picasso.with(context).load(country_flag.get(position)).into(flag);
                                        next.setVisibility(view.VISIBLE);
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Loc2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization",token);
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        };
        MySingleton.getmInstance(Loc2.this).addToRequestque(stringRequest);
    }


    // City Webservice //
    public void getCity()
    {
        /*String city_url="http://54.187.13.62/bitmoonbackend/public/index.php/api/user/cities?lang=en&country=";
        String city_urlA=city_url+country_list.get(position);*/
        String city_urlA=Base_Url+"/bitmoonbackend/public/index.php/api/user/cities?lang=";
        String city_urlB=city_urlA+LanGet+"&country=";
        String city_urlC=city_urlB+country_list.get(position);
        if(country_list.get(position).equals("United Arab Emirates"))
        {
            city_urlC=city_urlB+"United%20Arab%20Emirates";
        }
        else if(country_list.get(position).equals("الإمارات العربية المتحدة"))
        {
            city_urlC=city_urlB+"الإمارات%20العربية%20المتحدة";
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, city_urlC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            list.clear();
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                adapter = new LocationAdapter(Loc2.this, list);
                                listView.setAdapter(adapter);
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String name = actor.getString("city_name");
                                LocationModel dataSet = new LocationModel();
                                dataSet.setCity_name(actor.getString("city_name"));
                                dataSet.setCity_id(actor.getString("city_id"));
                                list.add(dataSet);
                            }
                            progressBar.setVisibility(View.GONE);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    city=list.get(position).getCity_name();
                                    city_id=list.get(position).getCity_id();

                                    for(int i=0; i<adapterView.getChildCount(); i++)
                                    {
                                        if(i == position)
                                        {
                                            adapterView.getChildAt(i).setBackgroundColor(Color.parseColor("#FFBF10"));
                                        }
                                        else
                                        {
                                            adapterView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                                        }
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Loc2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(Loc2.this).addToRequestque(stringRequest);
    }


    // City Update Webservice//
    public void getCityUpdate(){
        String url_city_update=Base_Url+"/bitmoonbackend/public/index.php/api/user/city";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("city_id",city_id);

            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url_city_update, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String status=jsonObject.getString("status");
                        Intent i=new Intent(getApplicationContext(),BottomTabs.class);
                        startActivity(i);
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
        country_text=(TextView)findViewById(R.id.country_text);
        flag=(ImageView)findViewById(R.id.country_flag);
        listView=(ListView)findViewById(R.id.list);
        city_text=(TextView)findViewById(R.id.city_text);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        Done=(Button) findViewById(R.id.done);
        next=(ImageView)findViewById(R.id.next_icon);
        prev=(ImageView)findViewById(R.id.prev_icon);
        loc_txt=(TextView)findViewById(R.id.location_select);
        city_label=(TextView)findViewById(R.id.city_text);
    }

    public void preferences()
    {
        PrefManager aa=new PrefManager(this);
        token=aa.getToken().toString();
        LanGet=aa.getLanguage().toString();
        if(LanGet.equals("en"))
        {
            loc_txt.setText("Select a Location");
            city_label.setText("Select City");
            Done.setText("Done");
        }
        else if(LanGet.equals("ar"))
        {
            loc_txt.setText("اختر موقعا");
            city_label.setText("اختر مدينة");
            Done.setText("فعله");
        }
    }

}
