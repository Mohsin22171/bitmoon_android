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


public class EgyptFragment extends Fragment {
    View view;
    Button Cairo;
    Button Alexandria;
    Button Ghardaqa;
    Button Sharm;
    Button Done;
    String city="aaa";
    ImageView Next;
    ImageView previous;
    String country="Egypt";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_egypt, container, false);
        Cairo=(Button)view.findViewById(R.id.city1);
        Alexandria=(Button)view.findViewById(R.id.city2);
        Ghardaqa=(Button)view.findViewById(R.id.city3);
        Sharm=(Button)view.findViewById(R.id.city4);
        Done=(Button)view.findViewById(R.id.done);
        Next=(ImageView)view.findViewById(R.id.next_icon);
        previous=(ImageView)view.findViewById(R.id.prev_icon);

        Cairo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cairo.setBackgroundColor(Color.parseColor("#FFBF10"));
                Alexandria.setBackgroundColor(Color.TRANSPARENT);
                Ghardaqa.setBackgroundColor(Color.TRANSPARENT);
                Sharm.setBackgroundColor(Color.TRANSPARENT);
                city="Cairo";
            }
        });

        Alexandria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alexandria.setBackgroundColor(Color.parseColor("#FFBF10"));
                Cairo.setBackgroundColor(Color.TRANSPARENT);
                Ghardaqa.setBackgroundColor(Color.TRANSPARENT);
                Sharm.setBackgroundColor(Color.TRANSPARENT);
                city="Alexandria";
            }
        });

        Ghardaqa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ghardaqa.setBackgroundColor(Color.parseColor("#FFBF10"));
                Alexandria.setBackgroundColor(Color.TRANSPARENT);
                Cairo.setBackgroundColor(Color.TRANSPARENT);
                Sharm.setBackgroundColor(Color.TRANSPARENT);
                city="Ghardaqa";
            }
        });

        Sharm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sharm.setBackgroundColor(Color.parseColor("#FFBF10"));
                Alexandria.setBackgroundColor(Color.TRANSPARENT);
                Cairo.setBackgroundColor(Color.TRANSPARENT);
                Ghardaqa.setBackgroundColor(Color.TRANSPARENT);
                city="Sharm";
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
                LoadFragment(new UAEFragment());
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
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
