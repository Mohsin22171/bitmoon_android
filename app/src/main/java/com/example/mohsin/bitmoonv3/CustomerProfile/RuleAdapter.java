package com.example.mohsin.bitmoonv3.CustomerProfile;


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
import com.example.mohsin.bitmoonv3.models.FoodOffers2;
import com.example.mohsin.bitmoonv3.models.RuleModel;

import java.util.List;

public class RuleAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<RuleModel> ruleModel;

    public RuleAdapter(Activity activity, List<RuleModel> ruleModel) {
        this.activity = activity;
        this.ruleModel = ruleModel;
    }

    @Override
    public int getCount() {
        return ruleModel.size();
    }

    @Override
    public Object getItem(int location) {
        return ruleModel.get(location);
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
            convertView = inflater.inflate(R.layout.rule_items, null);

        TextView rule_id=(TextView)convertView.findViewById(R.id.rule_id);
        TextView description=(TextView)convertView.findViewById(R.id.rule_description);
        RuleModel m=ruleModel.get(position);
        rule_id.setText(m.getRule_id());
        description.setText(m.getDescription());

        return convertView;
    }

    public boolean isEnabled(int position)
    {
        return false;
    }

}
