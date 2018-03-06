package com.example.mohsin.bitmoonv3.Location;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;


public class UAEFragment extends Fragment {
    View view;
    Button Dubai;
    Button Abu;
    Button Sharjah;
    Button Ajman;
    Button Ain;
    Button Done;
    String city="aaa";
    ImageView previous;
    String country="UAE";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_uae, container, false);
        Dubai=(Button)view.findViewById(R.id.city1);
        Abu=(Button)view.findViewById(R.id.city2);
        Ajman=(Button)view.findViewById(R.id.city3);
        Sharjah=(Button)view.findViewById(R.id.city4);
        // Ain=(Button)view.findViewById(R.id.city5);
        Done=(Button)view.findViewById(R.id.done);
        previous=(ImageView)view.findViewById(R.id.prev_icon);

        Dubai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dubai.setBackgroundColor(Color.parseColor("#FFBF10"));
                Abu.setBackgroundColor(Color.TRANSPARENT);
                Sharjah.setBackgroundColor(Color.TRANSPARENT);
                Ajman.setBackgroundColor(Color.TRANSPARENT);
                city="Dubai";
            }
        });

        Abu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Abu.setBackgroundColor(Color.parseColor("#FFBF10"));
                Dubai.setBackgroundColor(Color.TRANSPARENT);
                Sharjah.setBackgroundColor(Color.TRANSPARENT);
                Ajman.setBackgroundColor(Color.TRANSPARENT);
                city="Abu Dhabi";
            }
        });

        Ajman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ajman.setBackgroundColor(Color.parseColor("#FFBF10"));
                Abu.setBackgroundColor(Color.TRANSPARENT);
                Dubai.setBackgroundColor(Color.TRANSPARENT);
                Sharjah.setBackgroundColor(Color.TRANSPARENT);
                city="Ajman";
            }
        });

        Sharjah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sharjah.setBackgroundColor(Color.parseColor("#FFBF10"));
                Ajman.setBackgroundColor(Color.TRANSPARENT);
                Abu.setBackgroundColor(Color.TRANSPARENT);
                Dubai.setBackgroundColor(Color.TRANSPARENT);
                city="Sharjah";
            }
        });

     /*   Ain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ain.setBackgroundColor(Color.YELLOW);
                Ajman.setBackgroundColor(Color.TRANSPARENT);
                Abu.setBackgroundColor(Color.TRANSPARENT);
                Dubai.setBackgroundColor(Color.TRANSPARENT);
                Ain.setBackgroundColor(Color.TRANSPARENT);
                city="Sharjah";
            }
        });*/



        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(city.equals("aaa"))
                {
                    Intent ii = new Intent(getActivity(), BottomTabs.class);
                    getActivity().startActivity(ii);
                }
                else {
                    PrefManager aa = new PrefManager(getActivity());
                    aa.setCity(city);
                    Intent ii = new Intent(getActivity(), BottomTabs.class);
                    getActivity().startActivity(ii);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadFragment(new EgyptFragment());
            }
        });

        return view;

    }

    private void LoadFragment(Fragment fragment)
    {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
