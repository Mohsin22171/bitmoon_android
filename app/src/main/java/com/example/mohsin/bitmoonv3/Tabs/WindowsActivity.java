package com.example.mohsin.bitmoonv3.Tabs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mohsin.bitmoonv3.R;

/**
 * Created by Mohsin on 10/19/2017.
 */

public class WindowsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*TextView textview = new TextView(this);
        textview.setText("This is Windows tab");
        setContentView(textview);*/
        setContentView(R.layout.test);
    }
}
