package com.example.mohsin.bitmoonv3.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohsin.bitmoonv3.FoodDrink.FoodDetail2;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Data_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeRecyclerAdapter extends
        RecyclerView.Adapter<HomeRecyclerHolder> {
    private ArrayList<Data_Model> arrayList;
    private Context context;

    public HomeRecyclerAdapter(Context context,
                                ArrayList<Data_Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(HomeRecyclerHolder holder, final int position) {
        final Data_Model model = arrayList.get(position);

        final HomeRecyclerHolder mainHolder = (HomeRecyclerHolder) holder;

        mainHolder.title.setText(model.getName());
        Picasso.with(context).load(model.getImage()).resize(350, 124).into(mainHolder.imageView);
        //Picasso.with(context).load(model.getLogo()).resize(50, 50).into(mainHolder.logo);
        mainHolder.logo.setImageResource(model.getLogo2());

        mainHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String a = mainHolder.title.getText().toString();
                mainHolder.header.setText(a);*/

                String vendor_id=arrayList.get(position).getVendor_id_h();
                String vendor_name=arrayList.get(position).getName();
                PrefManager aa = new PrefManager(context);
                aa.setVendorId(vendor_id);
                aa.setVendorName(vendor_name);
                Intent i=new Intent(context,FoodDetail2.class);
                Bundle bundle=new Bundle();
                bundle.putString("Activity","Home");
                i.putExtras(bundle);
                context.startActivity(i);
                //context.overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }
        });


    }

    @Override
    public HomeRecyclerHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.home_item_row, viewGroup, false);
        HomeRecyclerHolder listHolder = new HomeRecyclerHolder(mainGroup);
        return listHolder;

    }


}