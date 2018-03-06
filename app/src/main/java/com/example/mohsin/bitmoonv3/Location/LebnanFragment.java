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


public class LebnanFragment extends Fragment {
    View view;
    Button Beirut;
    Button Tripoli;
    Button Jounieh;
    Button Done;
    String city="aaa";
    ImageView previous;
    ImageView next;
    String country="Lebnan";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lebnan, container, false);
        Beirut=(Button)view.findViewById(R.id.city1);
        Tripoli=(Button)view.findViewById(R.id.city2);
        Jounieh=(Button)view.findViewById(R.id.city3);
        Done=(Button)view.findViewById(R.id.done);
        previous=(ImageView)view.findViewById(R.id.prev_icon);
        next=(ImageView)view.findViewById(R.id.next_icon);

        Beirut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Beirut.setBackgroundColor(Color.parseColor("#FFBF10"));
                Tripoli.setBackgroundColor(Color.TRANSPARENT);
                Jounieh.setBackgroundColor(Color.TRANSPARENT);
                city="Beirut";
            }
        });

        Tripoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tripoli.setBackgroundColor(Color.parseColor("#FFBF10"));
                Beirut.setBackgroundColor(Color.TRANSPARENT);
                Jounieh.setBackgroundColor(Color.TRANSPARENT);
                city="Tripoli";
            }
        });

        Jounieh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Jounieh.setBackgroundColor(Color.parseColor("#FFBF10"));
                Tripoli.setBackgroundColor(Color.TRANSPARENT);
                Beirut.setBackgroundColor(Color.TRANSPARENT);
                city="Jounieh";
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

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadFragment(new JordanFragment());
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
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
