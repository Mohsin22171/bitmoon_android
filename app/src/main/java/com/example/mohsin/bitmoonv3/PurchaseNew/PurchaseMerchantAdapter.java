package com.example.mohsin.bitmoonv3.PurchaseNew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.Merchant_Model;
import com.example.mohsin.bitmoonv3.models.PurchaseModel;

import java.util.ArrayList;
import java.util.List;


public class PurchaseMerchantAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Merchant_Model> arrayList;
    private LayoutInflater inflater;
    private List<Merchant_Model> merchant_model;

    public PurchaseMerchantAdapter(Context context, List<Merchant_Model> merchant_model) {
        this.context = context;
        this.merchant_model = merchant_model;
    }

    @Override
    public int getCount() {
        return merchant_model.size();
    }

    @Override
    public Object getItem(int location) {
        return merchant_model.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.purchase_merchant_item, null);

        TextView res_name=(TextView)convertView.findViewById(R.id.food_hotel);
        TextView type=(TextView)convertView.findViewById(R.id.food_type);
        ImageView thumbnail=(ImageView)convertView.findViewById(R.id.food_icon);
        ImageView logoo=(ImageView)convertView.findViewById(R.id.logoo);
        Merchant_Model m=merchant_model.get(position);
        res_name.setText(m.getRes_name());
        type.setText(m.get_Type());
        thumbnail.setImageResource(m.getThumbmail());
        logoo.setImageResource(m.getLogoo());


        return convertView;
    }



}
