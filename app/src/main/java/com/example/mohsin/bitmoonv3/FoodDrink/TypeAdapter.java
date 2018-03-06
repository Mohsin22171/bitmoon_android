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
import com.example.mohsin.bitmoonv3.models.TypeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class TypeAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<TypeModel> type_model;
    ImageLoader imageLoader = Controller.getPermission().getImageLoader();
    private ArrayList<TypeModel> arrayList;

    public TypeAdapter(Activity activity, List<TypeModel> type_model) {
        this.activity = activity;
        this.type_model = type_model;
    }

    @Override
    public int getCount() {
        return type_model.size();
    }

    @Override
    public Object getItem(int location) {
        return type_model.get(location);
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
            convertView = inflater.inflate(R.layout.type_items, null);


        TextView types=(TextView)convertView.findViewById(R.id.types);
        TypeModel m=type_model.get(position);
        //types.setText(m.getItem_types());
        types.setText(m.get_TypeName());
        return convertView;
    }

}
