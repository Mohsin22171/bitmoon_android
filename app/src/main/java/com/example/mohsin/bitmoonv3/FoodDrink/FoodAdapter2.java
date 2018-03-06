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
import com.android.volley.toolbox.NetworkImageView;
import com.example.mohsin.bitmoonv3.Home.Controller;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.FoodModel;
import com.example.mohsin.bitmoonv3.models.FoodModel2;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FoodAdapter2 extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<FoodModel2> food_model;
    private ArrayList<FoodModel2> arrayList;
    ImageLoader imageLoader = Controller.getPermission().getImageLoader();
    private String test="a";


    public FoodAdapter2(Activity activity, List<FoodModel2> food_model) {
        this.activity = activity;
        this.food_model = food_model;
        this.arrayList=new ArrayList<FoodModel2>();
        this.arrayList.addAll(food_model);
    }

    @Override
    public int getCount() {
        return food_model.size();
    }

    @Override
    public Object getItem(int location) {
        return food_model.get(location);
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
            convertView = inflater.inflate(R.layout.activity_food_item, null);


        TextView name=(TextView)convertView.findViewById(R.id.food_hotel);
        TextView address=(TextView)convertView.findViewById(R.id.food_address);
        TextView distance=(TextView)convertView.findViewById(R.id.food_kilometer);
        ImageView icon=(ImageView)convertView.findViewById(R.id.food_icon);
        FoodModel2 m=food_model.get(position);
        name.setText(m.getVendor_name());
        address.setText(m.getAddress());
        distance.setText(m.getDistance());
        //icon.setImageResource(m.getVendor_avatar());
        Picasso.with(activity)
                .load(m.getVendor_avatar())
                .into(icon);



        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        food_model.clear();
        if (charText.length() == 0) {
            food_model.addAll(arrayList);
        } else {
            for (FoodModel2 ff : arrayList) {
                if (ff.getVendor_name().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    food_model.add(ff);
                }
            }
        }
        notifyDataSetChanged();
    }





}
