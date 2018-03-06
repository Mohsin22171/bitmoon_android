package com.example.mohsin.bitmoonv3.FoodDrink;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;

public class FoodPager2  extends FragmentStatePagerAdapter {

    int tabCount;

    public FoodPager2(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }




    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FoodOffersFragment2 tab1 = new FoodOffersFragment2();
                return tab1;
            case 1:
                FoodDetailFragment2 tab2 = new FoodDetailFragment2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
