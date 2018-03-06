package com.example.mohsin.bitmoonv3.Notification;

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
import com.example.mohsin.bitmoonv3.models.Notification_Model;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class NotificationAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Notification_Model> notification_models;

    public NotificationAdapter(Activity activity, List<Notification_Model> notification_models) {
        this.activity = activity;
        this.notification_models = notification_models;
    }

    @Override
    public int getCount() {
        return notification_models.size();
    }

    @Override
    public Object getItem(int location) {
        return notification_models.get(location);
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
            convertView = inflater.inflate(R.layout.notification_item, null);


        TextView Message=(TextView)convertView.findViewById(R.id.message);
        ImageView icon1=(ImageView)convertView.findViewById(R.id.avatar);
        Notification_Model m=notification_models.get(position);
        Message.setText(m.getMessage());
        return convertView;
    }
}
