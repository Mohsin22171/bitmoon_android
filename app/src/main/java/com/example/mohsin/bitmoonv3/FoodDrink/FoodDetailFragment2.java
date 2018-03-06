package com.example.mohsin.bitmoonv3.FoodDrink;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mohsin.bitmoonv3.Home.Controller;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.Home.HomeRecyclerAdapter;
import com.example.mohsin.bitmoonv3.Location.Location2;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Category_Model;
import com.example.mohsin.bitmoonv3.models.Data_Model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;

public class FoodDetailFragment2 extends Fragment implements OnMapReadyCallback {

    private View rootView;
    GoogleMap myMap;
    MapView mMapView;
    LatLng sydney;
    private static final String LOG_TAG = "ExampleApp";
    private static final String SERVICE_URL = "https://api.myjson.com/bins/4jb09";
    protected GoogleMap map;
    String lat, lng, vendor_city, address, LanGet;
    double latNew, lngNew;
    TextView food_city, food_address, hotel_details;
    String Base_Url;
    RelativeLayout relativeLayout;

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sydney = new LatLng(24.740101559281257, 46.66271209716797);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_food_details, container, false);

            Base_Url=getString(R.string.Base_Url);
            food_city=(TextView)rootView.findViewById(R.id.food_city);
            food_address=(TextView)rootView.findViewById(R.id.food_address);
            hotel_details=(TextView)rootView.findViewById(R.id.hotel_details);
            relativeLayout=(RelativeLayout)rootView.findViewById(R.id.main_relative);

            PrefManager prefManager=new PrefManager(getActivity());
            LanGet=prefManager.getLanguage();
            if(LanGet.equals("en"))
            {
                relativeLayout.setLayoutDirection(LTR);
            }
            else if(LanGet.equals("ar"))
            {
                relativeLayout.setLayoutDirection(RTL);
            }
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) rootView.findViewById(R.id.soleViewMap);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        } catch (InflateException e) {
            Log.e("Ex", "Inflate exception");
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        {
            myMap = googleMap;
            /*googleMap.addMarker(new MarkerOptions().position(sydney)
                    .title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(sydney).zoom(13).build();

            myMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));*/
            if (myMap != null) {
                new FoodDetailFragment2.MarkerTask().execute();
               // Marker();
            }

        }
    }

    public void Marker()
    {
        PrefManager aa=new PrefManager(getActivity());
        lat=aa.getLat().toString();
        lng=aa.getLng().toString();
        vendor_city=aa.getvendorCity().toString();
        address=aa.getvendorAddress().toString();

        food_city.setText(vendor_city);
        food_address.setText(address);
        if(LanGet.equals("en"))
        {
            hotel_details.setText("No Additional Details Found");
        }
        else if(LanGet.equals("ar"))
        {
            hotel_details.setText("لا تفاصيل إضافية تم العثور عليها");
        }

        if(lat!=null || lng!=null)
        {
            latNew = Double.parseDouble(lat);
            lngNew = Double.parseDouble(lng);
            Log.i("LatDouble",lat);
            sydney = new LatLng(latNew, lngNew);
        }
        else
        {
            sydney = new LatLng(0, 0);
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(sydney).zoom(13).build();

        myMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        myMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .position(sydney));

    }



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
                PrefManager aa=new PrefManager(getActivity());
                lat=aa.getLat().toString();
                lng=aa.getLng().toString();
                vendor_city=aa.getvendorCity().toString();
                address=aa.getvendorAddress().toString();

                food_city.setText(vendor_city);
                food_address.setText(address);
                if(LanGet.equals("en"))
                {
                    hotel_details.setText("No Additional Details Found");
                }
                else if(LanGet.equals("ar"))
                {
                    hotel_details.setText("لا تفاصيل إضافية تم العثور عليها");
                }
                Log.i("Response", vendor_city);
                Log.i("Response", address);
                if(lat!=null || lng!=null)
                {
                    latNew = Double.parseDouble(lat);
                    lngNew = Double.parseDouble(lng);
                    sydney = new LatLng(latNew, lngNew);
                }
                else
                {
                    sydney = new LatLng(0, 0);
                }
                // De-serialize the JSON string into an array of city objects
                myMap.clear();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Log.i("Response",jsonObj.toString());
                    if (i == 0) {
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(sydney).zoom(13).build();

                        myMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }


                    myMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .position(sydney));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }

        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}



    /*View view;
    private static final String LOG_TAG = "ExampleApp";
    private static final String SERVICE_URL = "https://api.myjson.com/bins/4jb09";
    protected GoogleMap map;
    String lat,lng,vendor_city,address,LanGet;
    double latNew,lngNew;
    TextView food_city,food_address,hotel_details;
    String Base_Url;
    LatLng sydney;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food_details, container, false);

        Base_Url=getString(R.string.Base_Url);
        food_city=(TextView)view.findViewById(R.id.food_city);
        food_address=(TextView)view.findViewById(R.id.food_address);
        hotel_details=(TextView)view.findViewById(R.id.hotel_details);

        PrefManager prefManager=new PrefManager(getActivity());
        LanGet=prefManager.getLanguage();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
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
            new FoodDetailFragment2.MarkerTask().execute();
            //Marker();
        }

    }


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
                PrefManager aa=new PrefManager(getActivity());
                lat=aa.getLat().toString();
                lng=aa.getLng().toString();
                vendor_city=aa.getvendorCity().toString();
                address=aa.getvendorAddress().toString();

                food_city.setText(vendor_city);
                food_address.setText(address);
                if(LanGet.equals("en"))
                {
                    hotel_details.setText("No Additional Details Found");
                }
                else if(LanGet.equals("ar"))
                {
                    hotel_details.setText("?? ?????? ?????? ?? ?????? ?????");
                }
                Log.i("Response", vendor_city);
                Log.i("Response", address);
                if(lat!=null || lng!=null)
                {
                    latNew = Double.parseDouble(lat);
                    lngNew = Double.parseDouble(lng);
                    sydney = new LatLng(latNew, lngNew);
                }
                else
                {
                    sydney = new LatLng(0, 0);
                }
                // De-serialize the JSON string into an array of city objects
                map.clear();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    Log.i("Response",jsonObj.toString());
                    if (i == 0) {
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(sydney).zoom(13).build();

                        map.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                    }


                    map.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                            .position(sydney));
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }

        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }*/
