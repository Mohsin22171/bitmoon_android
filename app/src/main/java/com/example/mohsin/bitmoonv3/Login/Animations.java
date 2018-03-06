package com.example.mohsin.bitmoonv3.Login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.mohsin.bitmoonv3.R;


public class Animations extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_test);

        final ImageView imageView=(ImageView)findViewById(R.id.image);
        ImageView next=(ImageView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView1=(ImageView)findViewById(R.id.image);
                TranslateAnimation animate = new TranslateAnimation(0,-imageView1.getWidth(),0,0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                imageView1.startAnimation(animate);
                imageView1.setVisibility(View.GONE);
            }
        });
    }
}
