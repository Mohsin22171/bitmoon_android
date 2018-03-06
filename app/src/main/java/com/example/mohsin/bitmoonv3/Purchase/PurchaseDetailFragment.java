package com.example.mohsin.bitmoonv3.Purchase;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodActivity;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodRecyclerAdapter;
import com.example.mohsin.bitmoonv3.Home.Controller;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.Home.HomeRecyclerAdapter;
import com.example.mohsin.bitmoonv3.Location.Location2;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Category_Model;
import com.example.mohsin.bitmoonv3.models.Data_Model;
import com.example.mohsin.bitmoonv3.models.PurchaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PurchaseDetailFragment extends Fragment {
    private static RecyclerView recyclerView;
    private static RecyclerView recyclerView2;
    private HomeRecyclerAdapter adapter2;
    private static final String url = "http://designhubstudios.com/bitmoon/getoffer.php";
    View view;
    int Header[];
    String Description[];
    String Heading[];
    private PurchaseDetailAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_purchase_detail, container, false);
        Heading=new String []
                {
                        "Include Best Hotels in Amman","New And Monthly Offers","Dummy"
                };

        Description=new String[]
                {
                        "Great Saving on hotels in Amman, Jordan online,Good availibility and great rates. Read hotel reviews and choose the best hotel deal for your stay.",
                        "Dummy","Dummy"

                };

        Header=new int[]
                {
                        R.drawable.supp_a,R.drawable.supp_b,R.drawable.restaurant_deals_header
                };
      /*  int a=Heading.length*250;
        int b=1100+a+1000;
        RelativeLayout Relative=(RelativeLayout)view.findViewById(R.id.a8);
        Relative.getLayoutParams().height=b;*/
        initViews();
        populatRecyclerView();
        return view;
    }

    private void initViews() {
        recyclerView = (RecyclerView)
                view.findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

       /* recyclerView2 = (RecyclerView)
                view.findViewById(R.id.recycler_view);
        recyclerView2.setHasFixedSize(true);
        recyclerView2
                .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));*/
    }


    private void populatRecyclerView() {
        final ArrayList<PurchaseModel> arrayList = new ArrayList<>();
        final ArrayList<Data_Model> arrayList2 =new ArrayList<>();

        for (int i = 0; i < Heading.length; i++) {
            arrayList.add(new PurchaseModel(Heading[i],Description[i],Header[i]));
        }
        adapter = new PurchaseDetailAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);

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
                                    adapter2 = new HomeRecyclerAdapter(getActivity(), arrayList2);
                                    //recyclerView2.setAdapter(adapter2);
                                    dataSet.setMore(obj.getString("more_to_enjoy"));
                                    dataSet.setName(obj.getString("name"));
                                    dataSet.setImage(url2 + obj.getString("cover"));
                                    dataSet.setLogo(url2 + obj.getString("logo"));
                                    arrayList2.add(dataSet);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter2.notifyDataSetChanged();
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


