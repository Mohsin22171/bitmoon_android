package com.example.mohsin.bitmoonv3.Location;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.mohsin.bitmoonv3.Home.Controller;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.FoodModel;
import com.example.mohsin.bitmoonv3.models.LocationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class LocationAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<LocationModel> location_model;

    public LocationAdapter(Activity activity, List<LocationModel> location_model) {
        this.activity = activity;
        this.location_model = location_model;
    }

    @Override
    public int getCount() {
        return location_model.size();
    }

    @Override
    public Object getItem(int location) {
        return location_model.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.location_item, null);


        TextView city_txt=(TextView)convertView.findViewById(R.id.city_txt);
        LocationModel m=location_model.get(position);
        city_txt.setText(m.getCity_name());
        return convertView;
    }
}
