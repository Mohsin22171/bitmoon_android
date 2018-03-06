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
import com.example.mohsin.bitmoonv3.models.TestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



public class TestAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<TestModel> testModel;
    ImageLoader imageLoader = Controller.getPermission().getImageLoader();

    public TestAdapter(Activity activity, List<TestModel> testModel) {
        this.activity = activity;
        this.testModel = testModel;
    }

    @Override
    public int getCount() {
        return testModel.size();
    }

    @Override
    public Object getItem(int location) {
        return testModel.get(location);
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
        final TestModel m=testModel.get(position);
        FilterText.setText(m.getText());
        FilterImage.setImageResource(m.getImage());

        return convertView;
    }


}
