package com.example.mohsin.bitmoonv3.InstructionsWalkThrough;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohsin.bitmoonv3.R;

public class Fragment_Walk2 extends Fragment {


    public static Fragment newInstance(Context context) {
        Fragment_Walk2 f = new Fragment_Walk2();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_walk2, null);
        return root;
    }

}