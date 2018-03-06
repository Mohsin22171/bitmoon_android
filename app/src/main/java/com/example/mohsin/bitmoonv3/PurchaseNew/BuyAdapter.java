package com.example.mohsin.bitmoonv3.PurchaseNew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.BuyModel;
import com.example.mohsin.bitmoonv3.models.PurchaseModel;

import java.util.ArrayList;
import java.util.List;


public class BuyAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<BuyModel> arrayList;
    private LayoutInflater inflater;
    private List<BuyModel> buy_model;

    public BuyAdapter(Context context, List<BuyModel> buy_model) {
        this.context = context;
        this.buy_model = buy_model;
    }

    @Override
    public int getCount() {
        return buy_model.size();
    }

    @Override
    public Object getItem(int location) {
        return buy_model.get(location);
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
            convertView = inflater.inflate(R.layout.buy_items, null);

        TextView Title=(TextView)convertView.findViewById(R.id.total);
        TextView Price=(TextView)convertView.findViewById(R.id.total_price);
        BuyModel m=buy_model.get(position);
        Title.setText(m.getTitle());
        Price.setText(m.getPrice());
        return convertView;
    }

}
