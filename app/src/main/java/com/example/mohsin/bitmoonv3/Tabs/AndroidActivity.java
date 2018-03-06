package com.example.mohsin.bitmoonv3.Tabs;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodActivity;
import com.example.mohsin.bitmoonv3.Home.Controller;
import com.example.mohsin.bitmoonv3.Home.HomeRecyclerAdapter;
import com.example.mohsin.bitmoonv3.Location.Location2;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.Data_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AndroidActivity extends AppCompatActivity {

    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_friends);
        Button Share=(Button)findViewById(R.id.share);
        Share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent activity3Intent = new Intent(v.getContext(),
                        ShareFriends.class);
                replaceContentView("activity3", activity3Intent);
            }
        });

    }

    public void replaceContentView(String id, Intent newIntent) {
        View view = getLocalActivityManager().startActivity(id,
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView();
        this.setContentView(view);
    }*/

    private static RecyclerView recyclerView;
    private static RecyclerView recyclerView2;
    private HomeRecyclerAdapter adapter;
    private HomeRecyclerAdapter adapter2;
    private static final String url = "http://designhubstudios.com/bitmoon/getoffer.php";
    private static String urlb="http://designhubstudios.com/bitmoon/pages/forms/";
    private static String navigateFrom;//String to get Intent Value
    ImageView ImageHeader;
    TextView City;
    Button drop;
    String country,city;
    ImageView FoodIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle=getIntent().getExtras();
        ImageHeader=(ImageView)findViewById(R.id.location_image);
        City=(TextView)findViewById(R.id.city);
        FoodIcon=(ImageView)findViewById(R.id.food_icon);
        FoodIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii=new Intent(getApplicationContext(), FoodActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("city",city);
                bundle.putString("country",country);
                ii.putExtras(bundle);
                startActivity(ii);
            }
        });
        if (bundle != null && bundle.getString("city") != null)
        {
            country=bundle.getString("country");
            city=bundle.getString("city");
            City.setText(city);
            String a=City.getText().toString();
            if(a.equals("Amman"))
            {
                ImageHeader.setImageResource(R.drawable.amman_header);
            }
            if(a.equals("Jerash"))
            {
                ImageHeader.setImageResource(R.drawable.jerash);
            }
            if(a.equals("Irbid"))
            {
                ImageHeader.setImageResource(R.drawable.irbid);
            }
            if(a.equals("Madaba"))
            {
                ImageHeader.setImageResource(R.drawable.madaba);
            }
            if(a.equals("Aqaba"))
            {
                ImageHeader.setImageResource(R.drawable.aqaba);
            }
            if(a.equals("Beirut"))
            {
                ImageHeader.setImageResource(R.drawable.beruit);
            }
            if(a.equals("Tripoli"))
            {
                ImageHeader.setImageResource(R.drawable.tripoli);
            }
            if(a.equals("Jounieh"))
            {
                ImageHeader.setImageResource(R.drawable.jounieh);
            }
            if(a.equals("Cairo"))
            {
                ImageHeader.setImageResource(R.drawable.cario);
            }
            if(a.equals("Alexandria"))
            {
                ImageHeader.setImageResource(R.drawable.alexandria);
            }
            if(a.equals("Ghardaqa"))
            {
                ImageHeader.setImageResource(R.drawable.ghardaqa);
            }
            if(a.equals("Sharm"))
            {
                ImageHeader.setImageResource(R.drawable.sharm);
            }
            if(a.equals("Dubai"))
            {
                ImageHeader.setImageResource(R.drawable.dubai);
            }
            if(a.equals("Abu Dhabi"))
            {
                ImageHeader.setImageResource(R.drawable.abu_dhabi);
            }
            if(a.equals("Ajman"))
            {
                ImageHeader.setImageResource(R.drawable.ajman);
            }
            if(a.equals("Sharjah"))
            {
                ImageHeader.setImageResource(R.drawable.sharjah);
            }
            if(a.equals("AL Ain"))
            {
                ImageHeader.setImageResource(R.drawable.al_ain);
            }
        }
        else
        {

        }
        drop=(Button) findViewById(R.id.drop);
        drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), Location2.class);
                Bundle bundle=new Bundle();
                bundle.putString("city",city);
                bundle.putString("country",country);
                i.putExtras(bundle);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });

        City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Location2.class);
                Bundle bundle=new Bundle();
                bundle.putString("city",city);
                bundle.putString("country",country);
                i.putExtras(bundle);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initViews();
        populatRecyclerView();
    }

    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Set Back Icon on Activity
        recyclerView = (RecyclerView)
                findViewById(R.id.recycler_view2);
        recyclerView.setHasFixedSize(true);
        getSupportActionBar().setTitle("Horizontal Recycler View");
        recyclerView
                .setLayoutManager(new LinearLayoutManager(AndroidActivity.this, LinearLayoutManager.HORIZONTAL, false));

        recyclerView2 = (RecyclerView)
                findViewById(R.id.recycler_view);
        recyclerView2.setHasFixedSize(true);
        getSupportActionBar().setTitle("Horizontal Recycler View");
        recyclerView2
                .setLayoutManager(new LinearLayoutManager(AndroidActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }


    private void populatRecyclerView() {
        final ArrayList<Data_Model> arrayList = new ArrayList<>();
        final ArrayList<Data_Model> arrayList2 =new ArrayList<>();


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
                                if(obj.getString("more_to_enjoy").equals("1"))
                                {
                                    adapter = new HomeRecyclerAdapter(AndroidActivity.this, arrayList);
                                    recyclerView.setAdapter(adapter);
                                    dataSet.setMore(obj.getString("more_to_enjoy"));
                                    dataSet.setName(obj.getString("name"));
                                    dataSet.setImage(url2 + obj.getString("cover"));
                                    dataSet.setLogo(url2 + obj.getString("logo"));
                                    arrayList.add(dataSet);
                                }
                                else {
                                    adapter2 = new HomeRecyclerAdapter(AndroidActivity.this, arrayList2);
                                    recyclerView2.setAdapter(adapter2);
                                    dataSet.setMore(obj.getString("more_to_enjoy"));
                                    dataSet.setName(obj.getString("name"));
                                    dataSet.setImage(url2 + obj.getString("cover"));
                                    dataSet.setLogo(url2 + obj.getString("logo"));
                                    arrayList2.add(dataSet);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder add = new AlertDialog.Builder(AndroidActivity.this);
                add.setMessage(error.getMessage()).setCancelable(true);
                AlertDialog alert = add.create();
                alert.setTitle("Error!!!");
                alert.show();
            }
        });
        Controller.getPermission().addToRequestQueue(billionaireReq);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
