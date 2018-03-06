package com.example.mohsin.bitmoonv3.FoodDrink;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mohsin.bitmoonv3.R;

public class FoodRecyclerHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public RelativeLayout linearLayout;
    public TextView er;
    public ImageView imageView;


    public FoodRecyclerHolder(View view) {
        super(view);

        this.title = (TextView) view
                .findViewById(R.id.title);
        this.linearLayout=(RelativeLayout) view.findViewById(R.id.linear);
        // this.er=(TextView)view.findViewById(R.id.er);
        this.imageView = (ImageView) view.findViewById(R.id.image);
    }
}