package com.example.mohsin.bitmoonv3;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.mohsin.bitmoonv3.Login.Login;
import com.example.mohsin.bitmoonv3.Login.LoginFinal;

public class MainActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,LoginFinal.class);
                startActivity(i);
            }
        }, SPLASH_TIME_OUT);
    }

}
