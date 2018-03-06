package com.example.mohsin.bitmoonv3.CustomerProfile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.CurrencyModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static android.R.layout.simple_spinner_item;


public class InformationActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String item="Choose Your Nationality";
    TextView Country;

    private EditText Date;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    CurrencyAdapter adapter = null;
    ArrayList<CurrencyModel> currency = null;
    ArrayList<CurrencyModel> currency1 = null;
    Spinner mySpinner ;
    String LanGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PrefManager prefManager=new PrefManager(getApplicationContext());
        LanGet=prefManager.getLanguage();
        if(LanGet.equals("en"))
        {
            setContentView(R.layout.activity_information2);
        }
        else if(LanGet.equals("ar"))
        {
            setContentView(R.layout.activity_information2);
        }
        currency = new ArrayList<>();
        currency = populateCustomerData(currency);
        mySpinner = (Spinner) findViewById( R.id.spinner_currency) ;
        adapter = new CurrencyAdapter(this, currency);
        mySpinner.setAdapter(adapter);
        mySpinner.setOnItemSelectedListener(this);


        currency1 = new ArrayList<>();
        currency1 = populateCustomerData1(currency1);
        Spinner mySpinner1 = (Spinner) findViewById( R.id.spinner_country) ;
        adapter = new CurrencyAdapter(this, currency1);
        mySpinner1.setAdapter(adapter);
        mySpinner1.setOnItemSelectedListener(this);

        ImageView back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), CustomerProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }
        });

        TextView update=(TextView)findViewById(R.id.MapText);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), CustomerProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
                finish();
            }
        });

        Date = (EditText) findViewById(R.id.date_txt);
        Date.setInputType(InputType.TYPE_NULL);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setDateTimeField();

       /* Spinner spinner = (Spinner) findViewById(R.id.spinner_country);
        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("JORDAN");
        categories.add("LEBNAN");
        categories.add("EGYPT");
        categories.add("UNITED ARAB EMIRATES");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

        String currency = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Selected: " + currency, Toast.LENGTH_SHORT).show();

        /*Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.country_txt)
        {
           String country = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Selected: " + country, Toast.LENGTH_SHORT).show();
        }
        else if(spinner.getId() == R.id.spinner_currency)
        {
            String currency = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), "Selected: " + currency, Toast.LENGTH_SHORT).show();
        }*/
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void setDateTimeField() {
        Date.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        toDatePickerDialog = new DatePickerDialog(this,R.style.MySpinnerDatePickerStyle,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                Date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        toDatePickerDialog.show();
    }


    public void onBackPressed()
    {
        Intent i = new Intent(InformationActivity.this, CustomerProfileActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);
        finish();
    }

    private ArrayList<CurrencyModel> populateCustomerData(ArrayList<CurrencyModel> currency) {
        /*currency.add(new CurrencyModel("", 1, R.drawable.jordan));
        currency.add(new CurrencyModel("", 2, R.drawable.lebanon));
        currency.add(new CurrencyModel("", 3, R.drawable.egypt));
        currency.add(new CurrencyModel("", 8, R.drawable.uae));*/
        return currency;
    }

    private ArrayList<CurrencyModel> populateCustomerData1(ArrayList<CurrencyModel> currency1) {
        /*currency1.add(new CurrencyModel("", 1, R.drawable.jordan));
        currency1.add(new CurrencyModel("", 2, R.drawable.lebanon));
        currency1.add(new CurrencyModel("", 3, R.drawable.egypt));
        currency1.add(new CurrencyModel("", 8, R.drawable.uae));*/
        return currency1;
    }


}

