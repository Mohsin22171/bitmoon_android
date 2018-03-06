package com.example.mohsin.bitmoonv3.FoodDrink;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.Attractions.AttractionActivity;
import com.example.mohsin.bitmoonv3.BeautyFitness.BeautyActivity;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.Hotels.HotelActivity;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.MainActivity;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.Retails.RetailActivity;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Category_Model;
import com.example.mohsin.bitmoonv3.models.FoodModel2;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FoodMaps extends FragmentActivity implements OnMapReadyCallback {
    private static final String LOG_TAG = "ExampleApp";
    private static final String SERVICE_URL = "https://api.myjson.com/bins/4jb09";
    protected GoogleMap map;
    String token;
    String sub_cat="1";
    String Base_Url;
    String Lang;

    /* Horizontal Recycler View Filter Attributes */
    public static final String[] TITLES= {"All","Arabic","Burger","Pizza","Chinese"};
    public static final Integer[] IMAGES= {R.drawable.all_picture,R.drawable.arabic_picture,R.drawable.burger,R.drawable.pizza,
            R.drawable.chinese};
    public static final String[] SubCatId={"1","2","3","4","5"};
    private static RecyclerView recyclerView;
    private FoodRecyclerAdapter adapter2;

    View ChildView;
    int RecyclerViewItemPosition ;
    TextView ListTxt,HeaderTxt,Just;
    ImageView Back;
    String Category,CategoryName;
    View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_map);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        Preferences();
        Bundle bundle=getIntent().getExtras();
        Category=bundle.getString("Category");
        Just=(TextView)findViewById(R.id.textView);
        HeaderTxt=(TextView)findViewById(R.id.FoodText);
        view1=(View)findViewById(R.id.View);
        ListTxt=(TextView)findViewById(R.id.MapText);
        if(Lang.equals("en"))
        {
            ListTxt.setText("List");
        }
        else if(Lang.equals("ar"))
        {
            ListTxt.setText("قائمة");
        }

        if(Category.equals("1"))
        {
            CategoryName="Food";
            if(Lang.equals("en"))
            {
                HeaderTxt.setText("Food & Drinks");
            }
            else if(Lang.equals("ar"))
            {
                HeaderTxt.setText("المأكولات والمشروبات");
            }
            recyclerView.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            Just.setVisibility(View.GONE);
        }
        else if(Category.equals("2"))
        {
            CategoryName="Attraction";
            if(Lang.equals("en"))
            {
                HeaderTxt.setText("Attractions & Leisures");
            }
            else if(Lang.equals("ar"))
            {
                HeaderTxt.setText("الجذب السياحي و ليسوريس");
            }
        }
        else if(Category.equals("3"))
        {
            CategoryName="Retail";
            if(Lang.equals("en"))
            {
                HeaderTxt.setText("Retail & Services");
            }
            else if(Lang.equals("ar"))
            {
                HeaderTxt.setText("خدمات التجزئة");
            }
        }
        else if(Category.equals("4"))
        {
            CategoryName="Hotel";
            if(Lang.equals("en"))
            {
                HeaderTxt.setText("Hotels & Travel");
            }
            else if(Lang.equals("ar"))
            {
                HeaderTxt.setText("الفنادق والسفر");
            }
        }
        else if(Category.equals("5"))
        {
            CategoryName="Beauty";
            if(Lang.equals("en"))
            {
                HeaderTxt.setText("Beauty & Fitness");
            }
            else if(Lang.equals("ar"))
            {
                HeaderTxt.setText("الجمال واللياقة البدنية");
            }
            recyclerView.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            Just.setVisibility(View.GONE);
        }

        Back=(ImageView)findViewById(R.id.back);
        Base_Url=getString(R.string.Base_Url);
        setUpMapIfNeeded();
        //populatRecyclerView();
        ListTxt.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(Category.equals("1"))
                {
                    Intent i=new Intent(getApplicationContext(),FoodActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();                }
                else if(Category.equals("2"))
                {
                    Intent i=new Intent(getApplicationContext(),AttractionActivity.class);
                    startActivity(i);
                }
                else if(Category.equals("3"))
                {
                    Intent i=new Intent(getApplicationContext(),RetailActivity.class);
                    startActivity(i);
                }
                else if(Category.equals("4"))
                {
                    Intent i=new Intent(getApplicationContext(),HotelActivity.class);
                    startActivity(i);
                }
                else if(Category.equals("5"))
                {
                    Intent i=new Intent(getApplicationContext(),BeautyActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                    finish();
                }
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);
        if (map != null) {
            //new MarkerTask().execute();
            MapFunc(sub_cat);
        }

    }

    private void initViews() {
        recyclerView = (RecyclerView)
                findViewById(R.id.filters);
        recyclerView.setHasFixedSize(true);

        recyclerView
                .setLayoutManager(new LinearLayoutManager(FoodMaps.this, LinearLayoutManager.HORIZONTAL, false));
    }

    /*private void populatRecyclerView() {
        final ArrayList<Category_Model> arrayList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            arrayList.add(new Category_Model(TITLES[i],IMAGES[i],SubCatId[i]));
        }
        adapter2 = new FoodRecyclerAdapter(FoodMaps.this, arrayList);
        recyclerView.setAdapter(adapter2);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);
                    sub_cat=arrayList.get(RecyclerViewItemPosition).getSubCatId();
                    MapFunc(sub_cat);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }*/


    private class MarkerTask extends AsyncTask<Void, Void, String> {
        private static final String LOG_TAG = "ExampleApp";
        private static final String SERVICE_URL = "https://api.myjson.com/bins/4jb09";

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(Void... args) {

            HttpURLConnection conn = null;
            final StringBuilder json = new StringBuilder();
            try {
                // Connect to the web service
                URL url = new URL(SERVICE_URL);
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(conn.getInputStream());

                // Read the JSON data into the StringBuilder
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    json.append(buff, 0, read);
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error connecting to service", e);
                //throw new IOException("Error connecting to service", e); //uncaught
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return json.toString();
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String json) {

            try {
                // De-serialize the JSON string into an array of city objects
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Log.i("Response",jsonObj.toString());

                   /* LatLng sydney = new LatLng(-34, 151);*/

                    LatLng latLng = new LatLng(jsonObj.getJSONArray("latlng").getDouble(0),
                            jsonObj.getJSONArray("latlng").getDouble(1));

                    //move CameraPosition on first result
                    if (i == 0) {
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(latLng).zoom(13).build();

                        map.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }


                    map.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .title(jsonObj.getString("name"))
                            .snippet(Integer.toString(jsonObj.getInt("population")))
                            .position(latLng));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }

        }
    }



    public void MapFunc(String sub_cat)
    {
        /*LatLng latLng = new LatLng(24.821711,67.034769);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(13).build();

        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .position(latLng));*/

        String SubId;
        SubId=sub_cat;
        final String LanGet="en";
        String food_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/"+Category+"/"+SubId+"/vendors?lang=";
        String food_urlA=food_url+LanGet+"&page=1&limit=10";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, food_urlA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                String response1=jsonObject.getString("response");
                                if(response1.equals("[]"))
                                {

                                }

                                else {
                                    map.clear();
                                    JSONArray jsonArray = new JSONArray(response1);
                                    Log.i("Response", jsonArray.toString());
                                    for (int i = 0; i < jsonArray.length(); i++)
                                    {
                                        JSONObject actor = jsonArray.getJSONObject(i);
                                        final String Vendor_id=actor.getString("vendor_id");
                                        final String Vendor_name=actor.getString("vendor_name");
                                        Double Lat=actor.getDouble("lat");
                                        Double Lng=actor.getDouble("long");
                                        String Vendor_Name=actor.getString("vendor_name");
                                        String Vendor_Address=actor.getString("address");
                                        LatLng latLng = new LatLng(Lat,Lng);

                                        if (i == 0) {
                                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                                    .target(latLng).zoom(13).build();

                                            map.animateCamera(CameraUpdateFactory
                                                    .newCameraPosition(cameraPosition));
                                        }


                                        map.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                                .title(Vendor_Name)
                                                .snippet(Vendor_id)
                                                .position(latLng));

                                        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                            @Override
                                            public void onInfoWindowClick(Marker marker) {
                                                String Vendor_id = marker.getSnippet();
                                                String Vendor_name=marker.getTitle();
                                                PrefManager aa = new PrefManager(getApplicationContext());
                                                aa.setVendorId(Vendor_id);
                                                aa.setVendorName(Vendor_name);
                                                Intent ii = new Intent(getApplicationContext(), FoodDetail2.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString("Activity", CategoryName);
                                                ii.putExtras(bundle);
                                                startActivity(ii);
                                                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                            }
                                        });
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FoodMaps.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
            MySingleton.getmInstance(FoodMaps.this).addToRequestque(stringRequest);
    }


    public void Preferences()
    {
        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();
        PrefManager prefManager1=new PrefManager(getApplicationContext());
        Lang=prefManager.getLanguage();
    }

    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
    }
}
