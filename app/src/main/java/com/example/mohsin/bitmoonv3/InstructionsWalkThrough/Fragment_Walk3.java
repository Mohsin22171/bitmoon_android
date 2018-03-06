package com.example.mohsin.bitmoonv3.InstructionsWalkThrough;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohsin.bitmoonv3.R;

public class Fragment_Walk3 extends Fragment {


    public static Fragment newInstance(Context context) {
        Fragment_Walk3 f = new Fragment_Walk3();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_walk3, null);
        return root;
    }

}