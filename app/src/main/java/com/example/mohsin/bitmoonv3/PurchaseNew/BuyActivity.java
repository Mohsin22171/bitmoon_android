package com.example.mohsin.bitmoonv3.PurchaseNew;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodDetail2;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.BuyModel;
import com.example.mohsin.bitmoonv3.models.FoodModel;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.duration;
import static android.R.layout.simple_spinner_item;
import static com.example.mohsin.bitmoonv3.Home.Controller.TAG;

public class BuyActivity extends Activity implements AdapterView.OnItemSelectedListener {
    String item;
    String Title[];
    String Price[];
    private List<BuyModel> list = new ArrayList<BuyModel>();
    private ListView listView;
    private BuyAdapter adapter;
    Spinner month;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        TextView Cancel=(TextView)findViewById(R.id.CancelText);
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BuyActivity.this, PurchaseActivity2.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
            }
        });

        Title = new String[]
                {
                        "Sub Total", "Grand Total"
                };
        Price = new String[]
                {
                        "USD 11.99", "USD 11.99"
                };

        for (int i = 0; i < Title.length; i++) {
            BuyModel ff = new BuyModel(Title[i], Price[i]);
            list.add(ff);
        }


        listView = (ListView) findViewById(R.id.buy_list);
        adapter = new BuyAdapter(this, list);
        listView.setAdapter(adapter);

        LayoutInflater inflater = getLayoutInflater();
        final ViewGroup header = (ViewGroup) inflater.inflate(R.layout.activity_buy_header, listView, false);
        listView.addHeaderView(header);

        TextView old=(TextView)findViewById(R.id.old_offer);
        old.setPaintFlags(old.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        LayoutInflater inflater1 = getLayoutInflater();
        final ViewGroup footer = (ViewGroup) inflater1.inflate(R.layout.activity_buy_footer, listView, false);

        final Button Checkout = (Button) findViewById(R.id.checkout_btn);
        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout.setVisibility(View.GONE);
                listView.addFooterView(footer);
                listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                listView.setStackFromBottom(true);

                Button Final=(Button)findViewById(R.id.confirm);
                Final.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(getApplicationContext(), BottomTabs.class);
                        startActivity(i);
                    }
                });


                month = (Spinner) findViewById(R.id.month_spinner);
                List<String> categories = new ArrayList<String>();
                categories.add("  Expiry Month");
                categories.add("  JAN");
                categories.add("  FEB");
                categories.add("  MAR");
                categories.add("  APR");
                categories.add("  MAY");
                categories.add("  JUN");
                categories.add("  JUL");
                categories.add("  AUG");
                categories.add("  SEP");
                categories.add("  OCT");
                categories.add("  NOV");
                categories.add("  DEC");

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.text, categories);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                month.setAdapter(dataAdapter);
                month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        item = parent.getItemAtPosition(position).toString();
                        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                Spinner year=(Spinner)findViewById(R.id.year_spinner);
                List<String> years = new ArrayList<String>();
                years.add("  Expiry Year");
                years.add("  2017");
                years.add("  2018");
                years.add("  2019");
                years.add("  2020");
                years.add("  2021");
                years.add("  2022");
                years.add("  2023");

                ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.text, years);
                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                year.setAdapter(dataAdapter1);
                year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        item = parent.getItemAtPosition(position).toString();
                       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("USD");
        categories.add("JOD");
        categories.add("QAR");
        categories.add("EGP");
        categories.add("SAR");
        categories.add("LBP");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void onBackPressed()
    {
        Intent i = new Intent(BuyActivity.this, PurchaseActivity2.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
    }

    }





