package com.example.mohsin.bitmoonv3.FoodDrink;

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
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.FoodModel;
import com.example.mohsin.bitmoonv3.models.FoodOffers;
import com.example.mohsin.bitmoonv3.models.FoodOffers2;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodOffersAdapter2 extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<FoodOffers2> food_offers;
    String LanGet;

    public FoodOffersAdapter2(Activity activity, List<FoodOffers2> food_offers) {
        this.activity = activity;
        this.food_offers = food_offers;
    }

    @Override
    public int getCount() {
        return food_offers.size();
    }

    @Override
    public Object getItem(int location) {
        return food_offers.get(location);
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
            convertView = inflater.inflate(R.layout.activity_food_offers_item, null);

        PrefManager prefManager=new PrefManager(activity);
        LanGet=prefManager.getLanguage();

        TextView offers=(TextView)convertView.findViewById(R.id.food_offers_offer);
        TextView date=(TextView)convertView.findViewById(R.id.food_offers_date);
        ImageView food_icon=(ImageView)convertView.findViewById(R.id.food_offers_icon);
        TextView offer_limit=(TextView)convertView.findViewById(R.id.offer_limit);
        FoodOffers2 m=food_offers.get(position);
        offers.setText(m.getOffer_name());
        offer_limit.setText(m.getOffer_limit());
        if(LanGet.equals("en")) {
            date.setText("Valid Until " + m.getEnd_date());
        }
        else if(LanGet.equals("ar"))
        {
            date.setText("صالحة حتى " + m.getEnd_date());
        }

        return convertView;
    }

}

