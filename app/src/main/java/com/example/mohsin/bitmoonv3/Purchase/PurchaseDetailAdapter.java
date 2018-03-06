package com.example.mohsin.bitmoonv3.Purchase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodRecyclerHolder;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.Category_Model;
import com.example.mohsin.bitmoonv3.models.PurchaseModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class PurchaseDetailAdapter extends
        RecyclerView.Adapter<PurchaseDetailHolder>  {
    private ArrayList<PurchaseModel> arrayList;
    private Context context;

    public PurchaseDetailAdapter(Context context,
                               ArrayList<PurchaseModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(PurchaseDetailHolder holder, final int position) {
        final PurchaseModel model = arrayList.get(position);

        final PurchaseDetailHolder mainHolder = (PurchaseDetailHolder) holder;// holder

        mainHolder.Heading.setText(model.getPurchaseHeading());
        mainHolder.Description.setText(model.getPurchaseDesciption());
        mainHolder.Header.setImageResource(model.getPurchaseHeader());


      /* mainHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String a=mainHolder.title.getText().toString();
               mainHolder.title.setText(a);
                Intent i=new Intent(context,LoginNew.class);
                context.startActivity(i);
            }
        });*/



    }

    @Override
    public PurchaseDetailHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.product_supplement_items, viewGroup, false);
        PurchaseDetailHolder listHolder = new PurchaseDetailHolder(mainGroup);
        return listHolder;

    }


}