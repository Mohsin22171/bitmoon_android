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
import com.example.mohsin.bitmoonv3.models.Purchase_History_Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Purchase_History_Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Purchase_History_Model> purchase_history_models;

    public Purchase_History_Adapter(Activity activity, List<Purchase_History_Model> purchase_history_models) {
        this.activity = activity;
        this.purchase_history_models = purchase_history_models;
    }

    @Override
    public int getCount() {
        return purchase_history_models.size();
    }

    @Override
    public Object getItem(int location) {
        return purchase_history_models.get(location);
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
            convertView = inflater.inflate(R.layout.purchase_item, null);

        TextView Offer_name=(TextView)convertView.findViewById(R.id.offer_name);
        TextView Offer_detail=(TextView)convertView.findViewById(R.id.offer_detail);
        Purchase_History_Model m=purchase_history_models.get(position);
        Offer_name.setText(m.getOffer_name());
        Offer_detail.setText(m.getOffer_detail());
        return convertView;
    }

}
