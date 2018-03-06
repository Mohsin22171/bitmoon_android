package com.example.mohsin.bitmoonv3.PurchaseNew;


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
import com.example.mohsin.bitmoonv3.models.PurchaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PurchaseDetailAdapter2 extends BaseAdapter{
    private Context context;
    private ArrayList<PurchaseModel>arrayList;
    private LayoutInflater inflater;
    private List<PurchaseModel> purchase_model;

    public PurchaseDetailAdapter2(Context context, List<PurchaseModel> purchase_model) {
        this.context = context;
        this.purchase_model = purchase_model;
    }

    @Override
    public int getCount() {
        return purchase_model.size();
    }

    @Override
    public Object getItem(int location) {
        return purchase_model.get(location);
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
            convertView = inflater.inflate(R.layout.product_supplement_items, null);

        TextView heading=(TextView)convertView.findViewById(R.id.supplement_heading);
        TextView description=(TextView)convertView.findViewById(R.id.supplement_description);
        ImageView header=(ImageView)convertView.findViewById(R.id.supplement_header);
        PurchaseModel m=purchase_model.get(position);
        heading.setText(m.getPurchaseHeading());
        description.setText(m.getPurchaseDesciption());
        header.setImageResource(m.getPurchaseHeader());


        return convertView;
    }

}
