package com.example.mohsin.bitmoonv3.FoodDrink;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.example.mohsin.bitmoonv3.Home.Controller;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.FilterModel;
import com.example.mohsin.bitmoonv3.models.FoodModel;
import com.example.mohsin.bitmoonv3.models.LevelModel;
import com.example.mohsin.bitmoonv3.models.TestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;


public class LevelAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<LevelModel> levelModel;

    public LevelAdapter(Activity activity, List<LevelModel> levelModel) {
        this.activity = activity;
        this.levelModel = levelModel;
    }

    @Override
    public int getCount() {
        return levelModel.size();
    }

    @Override
    public Object getItem(int location) {
        return levelModel.get(location);
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
            convertView = inflater.inflate(R.layout.level_item, null);

        final TextView Level_Name=(TextView)convertView.findViewById(R.id.level_name);
        final TextView Level_Desc=(TextView)convertView.findViewById(R.id.level_desc);
        final ImageView Level_Icon=(ImageView)convertView.findViewById(R.id.level_icon);
        final LevelModel m=levelModel.get(position);
        Level_Name.setText(m.getLevel_Name());
        Level_Desc.setText(m.getLevel_Desc());
        Level_Icon.setImageResource(m.getLevl_Icon());

        return convertView;
    }


}
