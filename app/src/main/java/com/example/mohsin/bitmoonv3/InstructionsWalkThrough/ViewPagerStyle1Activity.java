package com.example.mohsin.bitmoonv3.InstructionsWalkThrough;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mohsin.bitmoonv3.CustomerProfile.CustomerProfileActivity;
import com.example.mohsin.bitmoonv3.CustomerProfile.InformationActivity;
import com.example.mohsin.bitmoonv3.R;


public class ViewPagerStyle1Activity extends FragmentActivity {
    private ViewPager _mViewPager;
    private ViewPagerAdapter _adapter;
    private Button _btn1,_btn2,_btn3,_btn4;
    ImageView back;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_viewpager);

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CustomerProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }
        });

        setUpView();
        setTab();
    }
    private void setUpView(){
        _mViewPager = (ViewPager) findViewById(R.id.viewPager);
        _adapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager());
        _mViewPager.setAdapter(_adapter);
        _mViewPager.setCurrentItem(0);
        initButton();
    }
    private void setTab(){
        _mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

            @Override
            public void onPageScrollStateChanged(int position) {}
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                btnAction(position);
            }

        });

    }
    private void btnAction(int action){
        switch(action){
            case 0:
                /*setButton(_btn1,"1",40,40);
                setButton(_btn2,"",20,20);*/
                _btn1.setBackgroundColor(Color.BLUE);
                _btn2.setBackgroundColor(Color.GRAY);
                _btn3.setBackgroundColor(Color.GRAY);
                _btn4.setBackgroundColor(Color.GRAY);
                break;


            case 1:
                /*setButton(_btn2,"2",40,40);
                setButton(_btn1,"",20,20); */
                _btn2.setBackgroundColor(Color.BLUE);
                _btn1.setBackgroundColor(Color.GRAY);
                _btn3.setBackgroundColor(Color.GRAY);
                _btn4.setBackgroundColor(Color.GRAY);
                break;

            case 2:
                /*setButton(_btn2,"2",40,40);
                setButton(_btn1,"",20,20); */
                _btn3.setBackgroundColor(Color.BLUE);
                _btn1.setBackgroundColor(Color.GRAY);
                _btn2.setBackgroundColor(Color.GRAY);
                _btn4.setBackgroundColor(Color.GRAY);
                break;

            case 3:
                /*setButton(_btn2,"2",40,40);
                setButton(_btn1,"",20,20); */
                _btn4.setBackgroundColor(Color.BLUE);
                _btn1.setBackgroundColor(Color.GRAY);
                _btn2.setBackgroundColor(Color.GRAY);
                _btn3.setBackgroundColor(Color.GRAY);
                break;

        }
    }
    private void initButton(){
        _btn1=(Button)findViewById(R.id.btn1);
        _btn2=(Button)findViewById(R.id.btn2);
        _btn3=(Button)findViewById(R.id.btn3);
        _btn4=(Button)findViewById(R.id.btn4);
        _btn1.setBackgroundColor(Color.BLUE);
        _btn2.setBackgroundColor(Color.GRAY);
        _btn3.setBackgroundColor(Color.GRAY);
        _btn4.setBackgroundColor(Color.GRAY);
    }
    private void setButton(Button btn,String text,int h, int w){
        btn.setWidth(w);
        btn.setHeight(h);
        btn.setText(text);
    }


    public void onBackPressed()
    {
        Intent i = new Intent(getApplicationContext(), CustomerProfileActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
        finish();
    }
}