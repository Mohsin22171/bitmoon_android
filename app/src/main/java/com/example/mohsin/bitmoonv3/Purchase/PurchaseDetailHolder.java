package com.example.mohsin.bitmoonv3.Purchase;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.mohsin.bitmoonv3.R;

public class PurchaseDetailHolder extends RecyclerView.ViewHolder {
    public TextView Description;
    public LinearLayout linearLayout;
    public TextView Heading;
    public ImageView Header;


    public PurchaseDetailHolder(View view) {
        super(view);

        this.Description = (TextView) view
                .findViewById(R.id.supplement_description);
        //this.linearLayout=(LinearLayout)view.findViewById(R.id.linear);
        // this.er=(TextView)view.findViewById(R.id.er);
        this.Header = (ImageView) view.findViewById(R.id.supplement_header);
        this.Heading=(TextView)view.findViewById(R.id.supplement_heading);


    }
}