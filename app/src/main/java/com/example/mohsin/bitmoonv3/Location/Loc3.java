package com.example.mohsin.bitmoonv3.Location;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.mohsin.bitmoonv3.FoodDrink.FoodAdapter;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.FoodModel;
import com.example.mohsin.bitmoonv3.models.LocationModel;

import java.util.ArrayList;
import java.util.List;

public class Loc3 extends Activity {
    private List<LocationModel> list = new ArrayList<LocationModel>();
    private ListView listView;
    private LocationAdapter adapter;
    String[] city_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loc3);

      /*  city_name=new String[]
                {
                        "Sufra Restaurant","Haret Jdoudna","Reem Al-Bawadi Restaurant",
                        "Shawerma Reem","Melograno","Tannoureen"
                };

        for (int i=0;i<city_name.length;i++)
        {
            LocationModel ff=new LocationModel(city_name[i]);
            list.add(ff);
        }
        listView=(ListView)findViewById(R.id.list);
        adapter= new LocationAdapter(this,list);
        listView.setAdapter(adapter);*/
    }
}
