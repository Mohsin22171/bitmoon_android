package com.example.mohsin.bitmoonv3.InstructionsWalkThrough;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohsin.bitmoonv3.R;

public class Fragment_Walk1 extends Fragment {


    public static Fragment newInstance(Context context) {
        Fragment_Walk1 f = new Fragment_Walk1();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_walk1, null);
        return root;
    }

}
