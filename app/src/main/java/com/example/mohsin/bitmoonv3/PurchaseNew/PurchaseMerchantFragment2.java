package com.example.mohsin.bitmoonv3.PurchaseNew;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodActivity;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodAdapter;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodDetail2;
import com.example.mohsin.bitmoonv3.Home.Controller;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.Home.HomeRecyclerAdapter;
import com.example.mohsin.bitmoonv3.Location.Location2;
import com.example.mohsin.bitmoonv3.Purchase.PurchaseDetailAdapter;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Data_Model;
import com.example.mohsin.bitmoonv3.models.FoodModel;
import com.example.mohsin.bitmoonv3.models.Merchant_Model;
import com.example.mohsin.bitmoonv3.models.PurchaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PurchaseMerchantFragment2 extends Fragment {
    View view;
    private List<Merchant_Model> list = new ArrayList<Merchant_Model>();
    private PurchaseMerchantAdapter adapter;

    int thumbnail[];
    int logoo[];
    String res_name[];
    String type[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_purchase_merchant, container, false);

        res_name=new String[]
                {
                    "Sufra Restaurants","Haret Jdoudna","Reem Al-Bawadi Restaurant",
                        "TomYum Restaurant & cafee","Melograno","Tannouren"
                };

        type=new String[]
                {
                    "Arabic","","","chinese","",""
                };

        thumbnail=new int[]
                {
                  R.drawable.sufra,R.drawable.haret_icon,R.drawable.reem_icon,
                        R.drawable.tom,R.drawable.melograno,R.drawable.tannoureen_icon
                };

        logoo=new int[]
                {
                  R.drawable.food_icon,R.drawable.food_icon,R.drawable.food_icon,R.drawable.food_icon,
                        R.drawable.food_icon,R.drawable.food_icon
                };


                for (int i=0;i<res_name.length;i++)
                {
                    Merchant_Model ff=new Merchant_Model(res_name[i],type[i],thumbnail[i],logoo[i]);
                    list.add(ff);
                }

        ListView listView=(ListView)view.findViewById(R.id.ListView);
        adapter=new PurchaseMerchantAdapter(getActivity(),list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String a=list.get(position).getRes_name();
                Toast.makeText(getActivity(),"You clicked"+a,Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }



}

