package com.example.mohsin.bitmoonv3.FoodDrink;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.mohsin.bitmoonv3.Home.Controller;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.CuisineModel;
import com.example.mohsin.bitmoonv3.models.FoodModel;
import com.example.mohsin.bitmoonv3.models.TypeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;


public class CuisineAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<CuisineModel> cuisineModels;
    String Lang;
    Context context;

    public CuisineAdapter(Activity activity, List<CuisineModel> cuisineModels) {
        this.activity = activity;
        this.cuisineModels = cuisineModels;
        context=activity;
    }

    @Override
    public int getCount() {
        return cuisineModels.size();
    }

    @Override
    public Object getItem(int location) {
        return cuisineModels.get(location);
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
            convertView = inflater.inflate(R.layout.cuisine_items, null);

        RelativeLayout relativeLayout=(RelativeLayout)convertView.findViewById(R.id.cuisine_main);
        PrefManager prefManager=new PrefManager(context);
        Lang=prefManager.getLanguage();
        if(Lang.equals("en"))
        {
            relativeLayout.setLayoutDirection(LTR);
        }
        else if(Lang.equals("ar"))
        {
            relativeLayout.setLayoutDirection(RTL);
        }


        TextView types=(TextView)convertView.findViewById(R.id.types);
        ImageView Tick=(ImageView)convertView.findViewById(R.id.tick);
        Tick.setVisibility(View.GONE);
        CuisineModel m=cuisineModels.get(position);
        types.setText(m.getName());
        return convertView;
    }

}
