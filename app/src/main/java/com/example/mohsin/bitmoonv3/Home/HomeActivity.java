package com.example.mohsin.bitmoonv3.Home;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.Attractions.AttractionActivity;
import com.example.mohsin.bitmoonv3.BeautyFitness.BeautyActivity;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.CustomerProfile.ProfileActivity;
import com.example.mohsin.bitmoonv3.CustomerProfile.Utility;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodActivity;
import com.example.mohsin.bitmoonv3.Hotels.HotelActivity;
import com.example.mohsin.bitmoonv3.Location.Loc2;
import com.example.mohsin.bitmoonv3.Location.Location2;
import com.example.mohsin.bitmoonv3.Login.LoginFinal;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.Login.Register;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.Retails.RetailActivity;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Data_Model;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;


public class HomeActivity extends AppCompatActivity {
    // Attributes //
    private static RecyclerView recyclerView;
    private static RecyclerView recyclerView2;
    private HomeRecyclerAdapter adapter;
    private HomeRecyclerAdapter adapter2;
    TextView City;
    Button drop;
    String country,city,city_name,city_avatar,token;
    ImageView FoodIcon,BeautyIcon,AttractionIcon,RetailIcon,HotelIcon,ImageHeader,Settings;
    Context context=this;
    private ProgressBar progressBar;
    String city_id,Language;
    RelativeLayout relativeLayout;
    String LangGet="en";
    TextView FoodTxt,BeautyTxt,AttTxt,RetailTxt,HotelTxt,IndulgeTxt,PickTxt;
    ImageView Next,Prev;
    RelativeLayout Home_Slider;
    TextView Saving,Level;
    String Base_Url;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews2();
        PrefManager prefManager=new PrefManager(getApplicationContext());
        Base_Url=getString(R.string.Base_Url);
        String Lan=prefManager.getLanguage();
        preferences();
        progressBar.setVisibility(View.VISIBLE);
        Settings.setVisibility(View.VISIBLE);   // Settings Language //
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFunc();
            }
        });
        getSlider();

        Home_Slider.setVisibility(View.GONE);
        Prev.setVisibility(View.GONE);

        CheckInternet();
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*TranslateAnimation animate = new TranslateAnimation(0,-ImageHeader.getWidth(),0,0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                ImageHeader.startAnimation(animate);
                ImageHeader.setVisibility(View.GONE);
                TranslateAnimation animate2 = new TranslateAnimation(0,Home_Slider.getWidth(),0,0);
                animate2.setDuration(500);
                animate2.setFillAfter(true);
                Home_Slider.startAnimation(animate2);
                Next.setVisibility(View.GONE);
                Home_Slider.setVisibility(View.VISIBLE);
                Prev.setVisibility(View.VISIBLE);*/
                ImageHeader.setVisibility(View.GONE);
                Prev.setVisibility(View.VISIBLE);
                Home_Slider.setVisibility(View.VISIBLE);
            }
        });

        Prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* TranslateAnimation animate2 = new TranslateAnimation(0,Home_Slider.getWidth(),0,0);
                animate2.setDuration(500);
                animate2.setFillAfter(true);
                Home_Slider.startAnimation(animate2);
                ImageHeader.setVisibility(View.VISIBLE);*/
                Home_Slider.setVisibility(View.GONE);
                ImageHeader.setVisibility(View.VISIBLE);
            }
        });

        FoodIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(), FoodActivity.class);
                startActivity(ii);
            }
        });

        BeautyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this, BeautyActivity.class);
                startActivity(i);
            }
        });

        AttractionIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), AttractionActivity.class);
                startActivity(i);
            }
        });

        RetailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), RetailActivity.class);
                startActivity(i);
            }
        });

        HotelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), HotelActivity.class);
                startActivity(i);
            }
        });

        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), Loc2.class);
                Bundle bundle=new Bundle();
                bundle.putString("city",city);
                bundle.putString("country",country);
                i.putExtras(bundle);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });

        City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Loc2.class);
                Bundle bundle=new Bundle();
                bundle.putString("city",city);
                bundle.putString("country",country);
                i.putExtras(bundle);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initViews();
    }

    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        getSupportActionBar().setTitle("Horizontal Recycler View");
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView2 = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView2.setHasFixedSize(true);
        getSupportActionBar().setTitle("Horizontal Recycler View");
        recyclerView2.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void populatRecyclerView() {
        String home_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/home?lang=";
        String home_urlA=home_url+LangGet;
        final ArrayList<Data_Model> arrayList = new ArrayList<>();
        final ArrayList<Data_Model> arrayList2 =new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, home_urlA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            JSONObject jsonObject1=new JSONObject(response1);
                            city_name=jsonObject1.getString("city_name");
                            city_avatar=jsonObject1.getString("city_avatar");
                            city_id=jsonObject1.getString("city_id");
                            PrefManager aa=new PrefManager(getApplicationContext());
                            aa.setCityId(city_id);
                            City.setText(city_name);
                            Picasso.with(context).load(city_avatar).resize(282,180).into(ImageHeader);
                            String paid=jsonObject1.getString("paid");
                            String un_paid=jsonObject1.getString("un_paid");

                            // Featured //
                            JSONArray jsonArray=new JSONArray(paid);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                Data_Model dataSet = new Data_Model();
                                JSONObject actor = jsonArray.getJSONObject(i);
                                adapter = new HomeRecyclerAdapter(HomeActivity.this, arrayList);
                                recyclerView.setAdapter(adapter);
                                dataSet.setVendor_id_h(actor.getString("vendor_id"));
                                dataSet.setName(actor.getString("vendor_name"));
                                dataSet.setImage(actor.getString("background_image"));
                                dataSet.setCategory(actor.getString("category_id"));
                                String category = actor.getString("category_id");
                                if(category.equals("1")) {
                                    dataSet.setLogo2(R.drawable.food_two);
                                }
                                else
                                {
                                    dataSet.setLogo2(R.drawable.beauty_icon);
                                }
                                arrayList.add(dataSet);
                                String name = actor.getString("category_id");
                            }

                            // Indulge Your Self //
                            JSONArray jsonArray2=new JSONArray(un_paid);
                            for(int i=0;i<jsonArray2.length();i++)
                            {
                                Data_Model dataSet = new Data_Model();
                                JSONObject actor2 = jsonArray2.getJSONObject(i);
                                adapter2 = new HomeRecyclerAdapter(HomeActivity.this, arrayList2);
                                recyclerView2.setAdapter(adapter2);
                                dataSet.setVendor_id_h(actor2.getString("vendor_id"));
                                dataSet.setName(actor2.getString("vendor_name"));
                                dataSet.setImage(actor2.getString("background_image"));
                                dataSet.setCategory(actor2.getString("category_id"));
                                String category = actor2.getString("category_id");
                                if(category.equals("1")) {
                                    dataSet.setLogo2(R.drawable.food_two);
                                }
                                else
                                {
                                    dataSet.setLogo2(R.drawable.beauty_icon);
                                }
                                arrayList2.add(dataSet);
                                String name = actor2.getString("category_id");
                                Log.i("Response",name);
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(HomeActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(HomeActivity.this).addToRequestque(stringRequest);
    }

    private void SettingsFunc()
    {
        final CharSequence[] items = { "Arabic", "English"};

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Select Language");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(HomeActivity.this);
                PrefManager prefManager=new PrefManager(getApplicationContext());

                if (items[item].equals("Arabic"))
                {
                    Language="ar";
                    prefManager.setLanguage(Language);
                    preferences();
                    dialog.dismiss();
                    Intent i=new Intent(getApplicationContext(), BottomTabs.class);
                    startActivity(i);
                }
                else if (items[item].equals("English"))
                {
                    Language="en";
                    prefManager.setLanguage(Language);
                    preferences();
                    dialog.dismiss();
                    Intent i=new Intent(getApplicationContext(),BottomTabs.class);
                    startActivity(i);
                }
            }
        });
        builder.show();
    }

    public void getSlider()
    {
        String profile_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/savings";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, profile_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.i("Response",response1);
                            JSONObject jsonObject1=new JSONObject(response1);
                            Log.i("Savings",jsonObject1.toString());
                            String savings = jsonObject1.getString("total_savings");
                            String level=jsonObject1.getString("level");
                            Saving.setText(savings);
                            Level.setText(level);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getmInstance(HomeActivity.this).addToRequestque(stringRequest);
    }

    public void CheckInternet()
    {
        String answer = null;
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                answer="You are connected to a WiFi Network";
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                answer="You are connected to a Mobile Network";
        }
        else
        {
            answer = "No internet Connectivity";
            builder.setTitle("Network Error");
            builder.setMessage("Please check your Internet Connection");
            displayAlert("No Network");
        }
            //Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_LONG).show();
    }

    public void displayAlert(final String code)
    {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code.equals("No Network"))
                {
                    finish();
                    System.exit(0);
                }
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void preferences()
    {
        PrefManager aa=new PrefManager(this);
        city=aa.getCity().toString();
        token=aa.getToken().toString();
        LangGet=aa.getLanguage().toString();
        Log.i("Language",LangGet);
        if(LangGet.equals("en"))
        {
            relativeLayout.setLayoutDirection(LTR);
            FoodTxt.setText("  Food & \n  Drinks");
            BeautyTxt.setText("Beauty & \n  Fitness");
            AttTxt.setText("Attractions  \n  Leisure");
            RetailTxt.setText("Retail &  \n Services");
            HotelTxt.setText("Hotels &\n  Travel");
            IndulgeTxt.setText("Indulge Your Self");
            PickTxt.setText("Our Picks");
            populatRecyclerView();
        }
        else if(LangGet.equals("ar"))
        {
            relativeLayout.setLayoutDirection(RTL);
            FoodTxt.setText("طعام شراب");
            BeautyTxt.setText("        الجمال\n واللياقة البدنية");
            AttTxt.setText("  الجذب\n ليسوريس");
            RetailTxt.setText("خدمات التجزئة");
            HotelTxt.setText("فندق والسفر");
            IndulgeTxt.setText("تنغمس نفسك");
            PickTxt.setText("اختياراتنا");
            populatRecyclerView();
        }
    }

    public void initViews2()
    {
        ImageHeader=(ImageView)findViewById(R.id.location_image);
        City=(TextView)findViewById(R.id.city);
        FoodIcon=(ImageView)findViewById(R.id.food_icon);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        BeautyIcon=(ImageView)findViewById(R.id.beauty_icon);
        AttractionIcon=(ImageView)findViewById(R.id.attraction_icon);
        RetailIcon=(ImageView)findViewById(R.id.retail_icon);
        HotelIcon=(ImageView)findViewById(R.id.hotel_icon);
        drop=(Button) findViewById(R.id.drop);
        Settings=(ImageView)findViewById(R.id.settings);
        relativeLayout=(RelativeLayout)findViewById(R.id.main);
        FoodTxt=(TextView)findViewById(R.id.food_text);
        BeautyTxt=(TextView)findViewById(R.id.beauty_text);
        AttTxt=(TextView)findViewById(R.id.attraction_text);
        RetailTxt=(TextView)findViewById(R.id.retail_text);
        HotelTxt=(TextView)findViewById(R.id.hotels_text);
        IndulgeTxt=(TextView)findViewById(R.id.header1);
        PickTxt=(TextView)findViewById(R.id.header2);
        Next=(ImageView)findViewById(R.id.next);
        Home_Slider=(RelativeLayout)findViewById(R.id.home_slider);
        Prev=(ImageView)findViewById(R.id.prev);
        Saving=(TextView)findViewById(R.id.usd_saved);
        Level=(TextView)findViewById(R.id.usd_saved3);
        builder=new AlertDialog.Builder(HomeActivity.this);
    }




}

