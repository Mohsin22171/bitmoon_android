package com.example.mohsin.bitmoonv3.VendorFavourites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Fav_Model;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;


public class FavAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Fav_Model> food_model;
    double latitude,longitude;
    String address1,token;
    int favourite,VendorIdNew;
    String Vendor_id;
    String Base_Url;
    String LanGet;
    Context context;

    public FavAdapter(Activity activity, List<Fav_Model> food_model) {
        this.activity = activity;
        this.food_model = food_model;
        context=activity;
    }

    @Override
    public int getCount() {
        return food_model.size();
    }

    @Override
    public Object getItem(int location) {
        return food_model.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.fav_item, null);

        RelativeLayout relativeLayout=(RelativeLayout)convertView.findViewById(R.id.fav_main);
        PrefManager prefManager=new PrefManager(context);
        LanGet=prefManager.getLanguage();
        if(LanGet.equals("en"))
        {
            relativeLayout.setLayoutDirection(LTR);
        }
        else if(LanGet.equals("ar"))
        {
            relativeLayout.setLayoutDirection(RTL);
        }

        TextView name=(TextView)convertView.findViewById(R.id.food_hotel);
        TextView address=(TextView)convertView.findViewById(R.id.food_address);
        TextView distance=(TextView)convertView.findViewById(R.id.food_kilometer);
        ImageView icon=(ImageView)convertView.findViewById(R.id.food_icon);
        final ImageView fav_btn=(ImageView) convertView.findViewById(R.id.fav_btn);
        final ImageView un_fav_btn=(ImageView) convertView.findViewById(R.id.un_fav_btn);
        un_fav_btn.setVisibility(View.GONE);
        Fav_Model m=food_model.get(position);
        Log.i("Phone1", String.valueOf(position));

        name.setText(m.getVendorName());
        Picasso.with(activity).load(m.getAvatar()).into(icon);

        latitude= Double.parseDouble(m.getLat());
        longitude=Double.parseDouble(m.getLng());
        address.setText(m.getAddress());
        /*try {
                getAddress();
            } catch (IOException e) {
                e.printStackTrace();
            }
        address.setText(address1);*/

        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fav_btn.setVisibility(View.GONE);
                un_fav_btn.setVisibility(View.VISIBLE);
                Fav_Model fav_model=food_model.get(position);
                Vendor_id=fav_model.getVendorId();
                VendorIdNew=Integer.parseInt(Vendor_id);
                PrefManager prefManager=new PrefManager(activity);
                token=prefManager.getToken();
                getFavourite();
            }
        });

        return convertView;
    }


    public void getFavourite(){
        String url2="http://www.appstudeo.com/bitmoonbackend/public/index.php/api/user/vendor/favourite";
        try {
            favourite=0;
            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            JSONObject params = new JSONObject();
            params.put("vendor_id",VendorIdNew);
            params.put("favourite",0);

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


    /*public void getAddress() throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(activity, Locale.getDefault());

        addresses = geocoder.getFromLocation(latitude, longitude,1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
    }*/

}
