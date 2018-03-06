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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodActivity;
import com.example.mohsin.bitmoonv3.Home.Controller;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.Home.HomeRecyclerAdapter;
import com.example.mohsin.bitmoonv3.Location.Location2;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Data_Model;
import com.example.mohsin.bitmoonv3.models.PurchaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDetailFragment2 extends Fragment {
    View view;
    private List<PurchaseModel> list=new ArrayList<>();
    private PurchaseDetailAdapter2 adapter;
    String Heading[];
    String Description[];
    int Header[];
    private ListView listView;

    private static RecyclerView recyclerView;
    private HomeRecyclerAdapter adapter2;
    private static final String url = "http://designhubstudios.com/bitmoon/getoffer.php";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmet_purchase_detail2, container, false);

        Heading=new String []
                {
                        "Include Best Hotels in Amman","New And Monthly Offers","Include Best Hotels in Amman"
                };

        Description=new String[]
                {
                        "Great Saving on hotels in Amman, Jordan online,Good availibility and great rates. Read hotel reviews and choose the best hotel deal for your stay.",
                        "Discover the hottest places in the Jordan and get to know all of the different personalities that make up the heart and soul of our amazing community.",
                        "Great Saving on hotels in Amman, Jordan online,Good availibility and great rates. Read hotel reviews and choose the best hotel deal for your stay."


                };

        Header=new int[]
                {
                        R.drawable.supp_a,R.drawable.supp_b,R.drawable.supp_a
                };

        for(int i=0;i<Heading.length;i++)
        {
            PurchaseModel ff=new PurchaseModel(Heading[i],Description[i],Header[i]);
            list.add(ff);
        }

        listView=(ListView)view.findViewById(R.id.listview);
        adapter=new PurchaseDetailAdapter2(getActivity(),list);
        listView.setAdapter(adapter);
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.purchase_detail_header,listView,false);
        listView.addHeaderView(header);


        initViews();
        populatRecyclerView();
        return view;
    }

    private void initViews() {
        recyclerView = (RecyclerView)
                view.findViewById(R.id.recycler_view);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

    }

    private void populatRecyclerView() {
        final ArrayList<Data_Model> arrayList = new ArrayList<>();
        JsonArrayRequest billionaireReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                String url2="http://designhubstudios.com/bitmoon/pages/forms/";
                                JSONObject obj = response.getJSONObject(i);
                                Data_Model dataSet = new Data_Model();
                                dataSet.setMore(obj.getString("more_to_enjoy"));
                                adapter2 = new HomeRecyclerAdapter(getActivity(), arrayList);
                                recyclerView.setAdapter(adapter2);
                                dataSet.setMore(obj.getString("more_to_enjoy"));
                                dataSet.setName(obj.getString("name"));
                                dataSet.setImage(url2 + obj.getString("cover"));
                                dataSet.setLogo(url2 + obj.getString("logo"));
                                arrayList.add(dataSet);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder add = new AlertDialog.Builder(getActivity());
                add.setMessage(error.getMessage()).setCancelable(true);
                AlertDialog alert = add.create();
                alert.setTitle("Error!!!");
                alert.show();
            }
        });
        Controller.getPermission().addToRequestQueue(billionaireReq);
    }

}

