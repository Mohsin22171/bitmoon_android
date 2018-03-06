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
import com.example.mohsin.bitmoonv3.models.FoodModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FoodAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<FoodModel> food_model;
    ImageLoader imageLoader = Controller.getPermission().getImageLoader();
    private ArrayList<FoodModel> arrayList;

    public FoodAdapter(Activity activity, List<FoodModel> food_model) {
        this.activity = activity;
        this.food_model = food_model;
        this.arrayList=new ArrayList<FoodModel>();
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

       /* if (imageLoader == null)
            imageLoader = Controller.getPermission().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView worth = (TextView) convertView.findViewById(R.id.worth);
        TextView source = (TextView) convertView.findViewById(R.id.source);
        TextView year = (TextView) convertView.findViewById(R.id.inYear);
        DataSet m = DataList.get(position);
        thumbNail.setImageUrl(m.getImage(), imageLoader);
        name.setText(m.getName());
        source.setText("Wealth Source: " + String.valueOf(m.getSource()));
        worth.setText(String.valueOf(m.getWorth()));
        year.setText(String.valueOf(m.getYear()));*/

        TextView name=(TextView)convertView.findViewById(R.id.food_hotel);
        TextView address=(TextView)convertView.findViewById(R.id.food_address);
        TextView distance=(TextView)convertView.findViewById(R.id.food_kilometer);
        ImageView icon=(ImageView)convertView.findViewById(R.id.food_icon);
        FoodModel m=food_model.get(position);
        name.setText(m.getName());
        address.setText(m.getAddress());
        distance.setText(m.getDistance());
        icon.setImageResource(m.getIcon());


        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        food_model.clear();
        if (charText.length() == 0) {
            food_model.addAll(arrayList);
        } else {
            for (FoodModel ff : arrayList) {
                if (ff.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    food_model.add(ff);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filter2(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        food_model.clear();
        if (charText.length() == 0) {
            food_model.addAll(arrayList);
        } else {
            for (FoodModel ff : arrayList) {
                if (ff.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    food_model.add(ff);
                }
            }
        }
        notifyDataSetChanged();
    }



}
