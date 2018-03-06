package com.example.mohsin.bitmoonv3.CustomerProfile;

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
import com.example.mohsin.bitmoonv3.models.AddressModel;
import com.example.mohsin.bitmoonv3.models.FoodOffers;
import com.example.mohsin.bitmoonv3.models.Profile_Account_Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProfileAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Profile_Account_Model> profile_model;

    public ProfileAdapter(Activity activity, List<Profile_Account_Model> profile_model) {
        this.activity = activity;
        this.profile_model = profile_model;
    }

    @Override
    public int getCount() {
        return profile_model.size();
    }

    @Override
    public Object getItem(int location) {
        return profile_model.get(location);
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
            convertView = inflater.inflate(R.layout.profile_item, null);

        TextView ProfileItem=(TextView)convertView.findViewById(R.id.profile_item_txt);
        Profile_Account_Model m=profile_model.get(position);
        ProfileItem.setText(m.getProfileItem());
        return convertView;
    }

}
