package com.example.mohsin.bitmoonv3.Tabs;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mohsin.bitmoonv3.R;

public class FaviouritesTab extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        //textview.setText("This is BlackBerry tab");
        setContentView(R.layout.test);
    }
}
