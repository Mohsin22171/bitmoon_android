package com.example.mohsin.bitmoonv3.Location;

import android.app.Activity;
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


public class JordanFragment extends Fragment {
    View view;
    Button Amman;
    Button Jerash;
    Button Irbid;
    Button Madaba;
    Button Aqaba;
    Button Done;
    String city="aaa";
    ImageView Next;
    String country="Jordan";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_jordan, container, false);
        Amman=(Button)view.findViewById(R.id.city1);
        Jerash=(Button)view.findViewById(R.id.city2);
        Irbid=(Button)view.findViewById(R.id.city3);
        Madaba=(Button)view.findViewById(R.id.city4);
        Aqaba=(Button)view.findViewById(R.id.city5);
        Done=(Button)view.findViewById(R.id.done);
        Next=(ImageView)view.findViewById(R.id.next_icon);

        Amman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amman.setBackgroundColor(Color.parseColor("#FFBF10"));
                Jerash.setBackgroundColor(Color.TRANSPARENT);
                Irbid.setBackgroundColor(Color.TRANSPARENT);
                Madaba.setBackgroundColor(Color.TRANSPARENT);
                Aqaba.setBackgroundColor(Color.TRANSPARENT);
                city="Amman";
            }
        });

        Jerash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jerash.setBackgroundColor(Color.parseColor("#FFBF10"));
                Amman.setBackgroundColor(Color.TRANSPARENT);
                Irbid.setBackgroundColor(Color.TRANSPARENT);
                Madaba.setBackgroundColor(Color.TRANSPARENT);
                Aqaba.setBackgroundColor(Color.TRANSPARENT);
                city="Jerash";
            }
        });

        Irbid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Irbid.setBackgroundColor(Color.parseColor("#FFBF10"));
                Amman.setBackgroundColor(Color.TRANSPARENT);
                Jerash.setBackgroundColor(Color.TRANSPARENT);
                Madaba.setBackgroundColor(Color.TRANSPARENT);
                Aqaba.setBackgroundColor(Color.TRANSPARENT);
                city="Irbid";
            }
        });

        Madaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Madaba.setBackgroundColor(Color.parseColor("#FFBF10"));
                Amman.setBackgroundColor(Color.TRANSPARENT);
                Jerash.setBackgroundColor(Color.TRANSPARENT);
                Irbid.setBackgroundColor(Color.TRANSPARENT);
                Aqaba.setBackgroundColor(Color.TRANSPARENT);
                city="Madaba";
            }
        });

        Aqaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aqaba.setBackgroundColor(Color.parseColor("#FFBF10"));
                Madaba.setBackgroundColor(Color.TRANSPARENT);
                Amman.setBackgroundColor(Color.TRANSPARENT);
                Jerash.setBackgroundColor(Color.TRANSPARENT);
                Irbid.setBackgroundColor(Color.TRANSPARENT);
                city="Aqaba";
            }
        });

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

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadFragment(new LebnanFragment());
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
