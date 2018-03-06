package com.example.mohsin.bitmoonv3.FoodDrink;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.squareup.picasso.Picasso;

public class FoodShare extends Activity {
    String vendor_avatar,vendor_name,vendor_id,vendor_background_image;
    Context context=this;
    ImageView background,avatar,back;
    TextView ResName;
    String activity_name_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_share);
        initView();
        preferences();

        ResName.setText(vendor_name);
        Picasso.with(context).load(vendor_background_image).resize(400,200).into(background);
        Picasso.with(context).load(vendor_avatar).into(avatar);
        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodShare.this, FoodDetail2.class);
                Bundle bundle=new Bundle();
                bundle.putString("Activity",activity_name_1);
                i.putExtras(bundle);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }
        });
    }

    public void onBackPressed()
    {
        Intent i = new Intent(FoodShare.this, FoodDetail2.class);
        Bundle bundle=new Bundle();
        bundle.putString("Activity",activity_name_1);
        i.putExtras(bundle);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
        finish();
    }

    public void initView()
    {
        ResName=(TextView)findViewById(R.id.FoodText);
        background=(ImageView)findViewById(R.id.share_image_header);
        avatar=(ImageView)findViewById(R.id.share_avatar);
        back=(ImageView)findViewById(R.id.back);
    }

    public void preferences()
    {
        PrefManager aa=new PrefManager(getApplicationContext());
        vendor_name=aa.getvendorName().toString();
        activity_name_1=aa.getActivity_1();
        Bundle bundle=getIntent().getExtras();
        vendor_avatar=bundle.getString("vendor_avatar");
        vendor_background_image=bundle.getString("vendor_background_image");
    }
}
