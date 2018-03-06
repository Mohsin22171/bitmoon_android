package com.example.mohsin.bitmoonv3.CustomerProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.CurrencyModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class CurrencyAdapter extends ArrayAdapter<CurrencyModel> {
    ArrayList<CurrencyModel> customers, tempCustomer, suggestions;
    private Context context;


    public CurrencyAdapter(Context context, ArrayList<CurrencyModel> objects) {
        super(context, R.layout.customer_row, R.id.tvCustomer, objects);
        this.context=context;
        this.customers = objects;
        this.tempCustomer = new ArrayList<CurrencyModel>(objects);
        this.suggestions = new ArrayList<CurrencyModel>(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, null);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        CurrencyModel currencyModel = getItem(position);
        if (convertView == null) {
            if (parent == null)
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.customer_row, null);
            else
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.customer_row, parent, false);
        }
        TextView txtCustomer = (TextView) convertView.findViewById(R.id.tvCustomer);
        ImageView ivCustomerImage = (ImageView) convertView.findViewById(R.id.ivCustomerImage);
        if (txtCustomer != null)
            txtCustomer.setText(currencyModel.getFirstName() + " " + currencyModel.getLastName());


        if (ivCustomerImage != null)
            //ivCustomerImage.setImageResource(Integer.parseInt(currencyModel.getProfilePic()));
            Picasso.with(context).load(currencyModel.getProfilePic()).into(ivCustomerImage);

        return convertView;
    }
}