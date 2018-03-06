package com.example.mohsin.bitmoonv3.FoodDrink;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.Login.NewTest;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.BranchModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class Branches extends Activity {
    private ListView branch_listView;
    private BranchAdapter branch_adapter;
    private List<BranchModel> branch_list = new ArrayList<BranchModel>();
    String token;
    Context context=this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_activity);
        //branch_listView = (ListView) findViewById(R.id.listView);
        PrefManager aa = new PrefManager(getApplicationContext());
        token = aa.getToken().toString();
        getData();

        Button click=(Button)findViewById(R.id.Button);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                view  = getLayoutInflater().inflate(R.layout.custom_restaurant_location_popup, null);
                dialog.setContentView(view);
                branch_listView=(ListView)dialog.findViewById(R.id.lista);
                getData2();

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        branch_listView.setAdapter(null);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void getData2()
    {
        branch_adapter = new BranchAdapter(this, branch_list);
        branch_listView.setAdapter(branch_adapter);
    }

    public void getData() {
        String branch_url = "http://54.187.13.62/bitmoonbackend/public/index.php/api/user/vendor/branches?vendor_id=1&city_id=1&lang=en";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, branch_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            branch_list.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            String response1 = jsonObject.getString("response");
                            JSONObject jsonObject1 = new JSONObject(response1);
                            String branches = jsonObject1.getString("branches");
                            Log.i("Response", branches);
                            JSONArray jsonArray = new JSONArray(branches);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String branch_id = actor.getString("branch_name");
                                BranchModel branchModel = new BranchModel();
                                branchModel.setBranch_id(actor.getString("branch_id"));
                                branchModel.setBranch_Name(actor.getString("branch_name"));
                                branchModel.setLat(actor.getString("lat"));
                                branchModel.setLng(actor.getString("long"));
                                branch_list.add(branchModel);
                                Log.i("Response", branch_id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Branches.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type", "application/json");
                headers.put("Authorization", token);
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        };

        MySingleton.getmInstance(Branches.this).addToRequestque(stringRequest);
    }

/*    public void getAddress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        double latitude=24.8234446;
        double longitude=67.0404667;

        addresses = geocoder.getFromLocation(latitude, longitude,1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postalCode = addresses.get(0).getPostalCode();
        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        Toast.makeText(getApplicationContext(),address,Toast.LENGTH_SHORT).show();
    }*/

}
