package com.example.mohsin.bitmoonv3.PurchaseNew;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PurchasePager2 extends FragmentStatePagerAdapter {

    int tabCount;

    public PurchasePager2(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PurchaseDetailFragment2 tab1 = new PurchaseDetailFragment2();
                return tab1;
            case 1:
                PurchaseMerchantFragment2 tab2 = new PurchaseMerchantFragment2();
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
