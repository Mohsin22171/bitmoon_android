package com.example.mohsin.bitmoonv3.FoodDrink;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.FilterModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;


public class FilterAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<FilterModel> filter_model;
    private ArrayList<FilterModel> arrayList;
    private List<Integer> Item100;
    Context context;
    String Lang;

    public FilterAdapter(Activity activity, List<FilterModel> filter_model) {
        this.activity = activity;
        this.filter_model = filter_model;
        this.arrayList=new ArrayList<FilterModel>();
        this.arrayList.addAll(filter_model);
        context=activity;
    }


    public void func(List<Integer>Item100)
    {
        Item100.addAll(Item100);
        Log.d("Item100", String.valueOf(Item100));
    }

    @Override
    public int getCount() {
        return filter_model.size();
    }

    @Override
    public Object getItem(int location) {
        return filter_model.get(location);
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
            convertView = inflater.inflate(R.layout.filter_item, null);


        final TextView FilterText=(TextView)convertView.findViewById(R.id.FilterText);
        final ImageView FilterImage=(ImageView)convertView.findViewById(R.id.FilterImage);
        ImageView Tick=(ImageView)convertView.findViewById(R.id.tick);
        final FilterModel m=filter_model.get(position);
        RelativeLayout relativeLayout=convertView.findViewById(R.id.filter_main);

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

        String Additional=prefManager.getAdditionalArray();
        Additional=Additional.replace("[", "").replace("]", "");
        List<String> Item1=new ArrayList<>();
        Item1.add(Additional);

        String[] elements = Additional.split(",");
        List<String> fixedLenghtList = Arrays.asList(elements);
        Log.i("Position", String.valueOf(fixedLenghtList));

        ArrayList<Integer> result = new ArrayList<Integer>();
        Log.d("Ad",result.toString());
        for(int ii=0;ii<Item1.size();ii++)
        {
            Log.i("AB",Item1.get(ii));
        }
        List<Integer> Item2=new ArrayList<>();
        Item2.add(0);
        Item2.add(1);
        Item2.add(100);
        Item2.add(2);
        if(Item100!=null) {
            for (int i = 0; i < Item100.size(); i++) {
                if (position == Item100.get(i)) {
                    Log.i("Position", String.valueOf(position));
                    Tick.setVisibility(View.VISIBLE);
                    Log.i("Position2","Match");
                }
                else
                {
                    Log.i("Position2","UnMatch");

                }
            }
        }
        FilterText.setText(m.getName());
        //FilterImage.setImageResource(m.getIcon2());


        return convertView;
    }

    public void Filter2(List<String> Item1)
    {
        String a;
        Log.i("Item1", String.valueOf(Item1.size()));
        filter_model.clear();

        int i=0;
        for (FilterModel ff : arrayList) {
                if(i==Item1.size())
                {
                    //Log.i("Item","Yes");
                }
                else {
                    //Log.i("Item","No");
                    if (ff.getName().contains(Item1.get(i))) {
                        a=ff.getName().concat(Item1.get(i));
                        //Item1.add(a);
                        filter_model.add(ff);
                        Log.i("Item1", Item1.toString());
                        i++;
                    } else {
                        //Log.i("Item1", "Un Match");
                    }
                   // Log.i("Item", String.valueOf(i));
                }
            }
        notifyDataSetChanged();

        /*filter_model.clear();
        if (Item1.size() == 0) {
            filter_model.addAll(arrayList);
        } else {
            for (FilterModel ff : arrayList) {
                if (ff.getItem2().toLowerCase(Locale.getDefault()).contains(Item1.get(0)))
                {
                    filter_model.add(ff);
                    Log.i("Item1",filter_model.toString());
                }
                else
                {
                    Log.i("Item1",filter_model.toString());
                }
            }
        }
        notifyDataSetChanged();*/
    }


}
