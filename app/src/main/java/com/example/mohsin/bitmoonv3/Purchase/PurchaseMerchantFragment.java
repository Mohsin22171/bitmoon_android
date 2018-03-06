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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PurchaseMerchantFragment extends Fragment {
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragmet_purchase_merchant, container, false);
        return view;
    }

}


