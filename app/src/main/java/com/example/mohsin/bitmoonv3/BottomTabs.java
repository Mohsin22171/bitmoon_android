package com.example.mohsin.bitmoonv3;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.mohsin.bitmoonv3.CustomerProfile.ProfileActivity;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.Notification.Notification;
import com.example.mohsin.bitmoonv3.Tabs.ProductsTab;
import com.example.mohsin.bitmoonv3.VendorFavourites.FavouriteActivity;

public class BottomTabs extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tabs);
        Resources ressources = getResources();
        TabHost tabHost = getTabHost();

        // Android tab
        /*Intent intentAndroid = new Intent().setClass(this, AndroidActivity.class);*/
        Intent intentAndroid=new Intent(this,HomeActivity.class);
        TabHost.TabSpec tabSpecAndroid = tabHost
                .newTabSpec("Android")
                .setIndicator("", ressources.getDrawable(R.drawable.icon_android_config))
                .setContent(intentAndroid);

        // Apple tab
        Intent intentApple = new Intent().setClass(this, Notification.class);
        TabHost.TabSpec tabSpecApple = tabHost
                .newTabSpec("Apple")
                .setIndicator("", ressources.getDrawable(R.drawable.icon_apple_config))
                .setContent(intentApple);

        // Windows tab
        Intent intentWindows = new Intent().setClass(this, ProfileActivity.class);
        TabHost.TabSpec tabSpecWindows = tabHost
                .newTabSpec("Windows")
                .setIndicator("", ressources.getDrawable(R.drawable.icon_windows_config))
                .setContent(intentWindows);

        // Blackberry tab
        Intent intentBerry = new Intent().setClass(this, FavouriteActivity.class);
        TabHost.TabSpec tabSpecBerry = tabHost
                .newTabSpec("Berry")
                .setIndicator("", ressources.getDrawable(R.drawable.icon_blackberry_config))
                .setContent(intentBerry);

        /*Intent intentNew = new Intent().setClass(this, ProductsTab.class);
        TabHost.TabSpec tabIntentNew = tabHost
                .newTabSpec("Berry")
                .setIndicator("", ressources.getDrawable(R.drawable.icon_new_config))
                .setContent(intentNew);*/

        // add all tabs
        tabHost.addTab(tabSpecAndroid);
        tabHost.addTab(tabSpecApple);
        tabHost.addTab(tabSpecBerry);
        tabHost.addTab(tabSpecWindows);
        //tabHost.addTab(tabIntentNew);

        //set Windows tab as default (zero based)
        tabHost.setCurrentTab(0);

        Bundle bundle=getIntent().getExtras();
        String response;
        if (bundle != null && bundle.getString("name") != null)
        {
            response=bundle.getString("name");
            if(response.equals("Profile"))
            {
                tabHost.setCurrentTab(3);
            }
            else if(response.equals("Favourite"))
            {
                tabHost.setCurrentTab(2);
            }
            else
            {
                tabHost.setCurrentTab(0);
            }
        }
        else
        {

        }

    }

}
