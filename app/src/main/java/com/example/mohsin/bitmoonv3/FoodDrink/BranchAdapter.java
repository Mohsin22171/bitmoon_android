package com.example.mohsin.bitmoonv3.FoodDrink;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.BranchModel;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;

public class BranchAdapter extends BaseAdapter {
    private Activity activity;
    String address;
    private List<BranchModel> address_model;
    private LayoutInflater inflater;
    double latitude;
    double longitude;
    String LanGet;

    public BranchAdapter(Activity activity, List<BranchModel> address_model) {
        this.activity = activity;
        this.address_model = address_model;
    }

    public int getCount() {
        return this.address_model.size();
    }

    public Object getItem(int location) {
        return this.address_model.get(location);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.activity.getSystemService("layout_inflater");
        }
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.address_item, null);
        }

        final TextView Street = (TextView) convertView.findViewById(R.id.street);
        TextView Distance = (TextView) convertView.findViewById(R.id.distance);
        BranchModel m = (BranchModel) this.address_model.get(position);
        ((TextView) convertView.findViewById(R.id.address)).setText(m.getBranch_Name());
        this.latitude = Double.parseDouble(m.getLat());
        this.longitude = Double.parseDouble(m.getLng());
        Log.i("Latitude", String.valueOf(this.latitude));
        Log.i("Latitude1", String.valueOf(this.longitude));

        MySingleton.getmInstance(this.activity).addToRequestque(new StringRequest(0, "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + this.latitude + "," + this.longitude, new Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(new JSONObject(response).getString("results"));
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String name = jsonArray.getJSONObject(0).getString("formatted_address");
                        Street.setText(name);
                        Log.i("Pakistan", name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BranchAdapter.this.activity, "No Internet Connection", 1).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
        });
        return convertView;
    }
}