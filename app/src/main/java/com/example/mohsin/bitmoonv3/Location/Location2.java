package com.example.mohsin.bitmoonv3.Location;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.R;

public class Location2 extends Activity{
    String country,city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Bundle bundle=getIntent().getExtras();
        if (bundle != null && bundle.getString("city") != null)
        {
            country=bundle.getString("country");
            city=bundle.getString("city");
        }
        else
        {

        }
        LoadFragment(new JordanFragment());
    }

    private void LoadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() !=0) {
            Intent ii=new Intent(getApplicationContext(), BottomTabs.class);
            Bundle bundle=new Bundle();
            bundle.putString("city",city);
            bundle.putString("country",country);
            ii.putExtras(bundle);
            startActivity(ii);
        } else {
            //getFragmentManager().popBackStack();
        }
    }
}
