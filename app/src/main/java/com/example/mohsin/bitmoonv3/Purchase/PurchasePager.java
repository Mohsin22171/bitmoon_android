package com.example.mohsin.bitmoonv3.Purchase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class PurchasePager extends FragmentStatePagerAdapter {

    int tabCount;

    public PurchasePager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PurchaseDetailFragment tab1 = new PurchaseDetailFragment();
                return tab1;
            case 1:
                PurchaseMerchantFragment tab2 = new PurchaseMerchantFragment();
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
