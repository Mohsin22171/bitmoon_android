package com.example.mohsin.bitmoonv3.FoodDrink;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Category_Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FoodRecyclerAdapter extends
        RecyclerView.Adapter<FoodRecyclerHolder>  {
    private ArrayList<Category_Model> arrayList;
    private Context context;

    public FoodRecyclerAdapter(Context context,
                                ArrayList<Category_Model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(FoodRecyclerHolder holder, final int position) {
        final Category_Model model = arrayList.get(position);

        final FoodRecyclerHolder mainHolder = (FoodRecyclerHolder) holder;// holder

        mainHolder.title.setText(model.getName());
        mainHolder.imageView.setImageResource(model.getImage2());


       /*mainHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String subCatId=arrayList.get(position).getSubCatId();
                FoodActivity foodActivity=new FoodActivity();
                foodActivity.getData();
            }
        });*/

    }

    @Override
    public FoodRecyclerHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.item_row, viewGroup, false);
        FoodRecyclerHolder listHolder = new FoodRecyclerHolder(mainGroup);
        return listHolder;

    }


}