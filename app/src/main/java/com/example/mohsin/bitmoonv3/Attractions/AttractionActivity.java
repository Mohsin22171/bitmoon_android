package com.example.mohsin.bitmoonv3.Attractions;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.BeautyFitness.BeautyActivity;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.FoodDrink.CuisineAdapter;
import com.example.mohsin.bitmoonv3.FoodDrink.FilterAdapter;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodActivity;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodAdapter2;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodDetail2;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodRecyclerAdapter;
import com.example.mohsin.bitmoonv3.FoodDrink.TypeAdapter;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Category_Model;
import com.example.mohsin.bitmoonv3.models.CuisineModel;
import com.example.mohsin.bitmoonv3.models.FilterModel;
import com.example.mohsin.bitmoonv3.models.FoodModel2;
import com.example.mohsin.bitmoonv3.models.TypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;

public class AttractionActivity extends Activity{
    /* Main Food Listing Attributes */
    private ListView listView;
    private List<FoodModel2> list=new ArrayList<FoodModel2>();
    private FoodAdapter2 adapter;
    String token;
    EditText editsearch;
    ImageView Back;
    final Context context = this;
    private ProgressBar progressBar;
    String LanGet;
    TextView Map,FoodTxt,JustShow,Result;
    ImageView Filter;
    RelativeLayout relativeLayout;

    /* Vertical Search Filter Attributes*/
    private List<FilterModel>FilterList=new ArrayList<>();
    private FilterAdapter filterAdapter;
    private ListView additional_list;

    /* Type Filter Attributes*/
    private TypeAdapter type_adapter;
    private List<TypeModel> list_type=new ArrayList<>();
    String Type="jjjh";
    private  ListView type_listView;
    private List<String>Item1=new ArrayList<String>();

    /*Cuisine Filter */
    private ListView cuisine_listView;
    private List<CuisineModel>list_cuisine=new ArrayList<>();
    private CuisineAdapter cuisineAdapter;
    private List<String>CuisineItem=new ArrayList<String>();
    Dialog cuisine_dialog;
    TextView Done1;
    String Base_Url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_attraction);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews2();
        preferences();
        Base_Url=getString(R.string.Base_Url);

        Result.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        getData();
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), BottomTabs.class);
                startActivity(i);
            }
        });

        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent i=new Intent(FoodActivity.this,FoodMaps.class);
                startActivity(i);*/
                String text="Burger";
                text.toLowerCase(Locale.getDefault());
                //adapter.filter2(text);
            }
        });

        Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context,R.style.Theme_Dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                dialog.setContentView(R.layout.custom_filer2);
                dialog.setTitle("Title...");
                additional_list=(ListView)dialog.findViewById(R.id.lista);
                get_Additional_Details();

                final TextView Type_Txt=(TextView)dialog.findViewById(R.id.textView5);
                TextView Type_Button_Txt=(TextView) dialog.findViewById(R.id.textView4);
                TextView Additional_Txt=(TextView)dialog.findViewById(R.id.textView2);
                RelativeLayout filter_relative=(RelativeLayout)dialog.findViewById(R.id.filter2_main_layout);
                TextView FilterTxt=(TextView)dialog.findViewById(R.id.FilterText);

                if(LanGet.equals("en"))
                {
                    Type_Txt.setText("Type");
                    Type_Button_Txt.setText("SELECT TYPE");
                    Additional_Txt.setText("ADDITIONAL DETAILS");
                    filter_relative.setLayoutDirection(LTR);
                    FilterTxt.setText("FILTERS");
                }
                else if(LanGet.equals("ar"))
                {
                    Type_Txt.setText("اكتب");
                    Type_Button_Txt.setText("اختر صنف");
                    Additional_Txt.setText("تفاصيل اضافية");
                    filter_relative.setLayoutDirection(RTL);
                    FilterTxt.setText("مرشحات");
                }

                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.show();

                final TextView type_txt=(TextView)dialog.findViewById(R.id.type_txt);

                Button type=(Button) dialog.findViewById(R.id.type);
                type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog1 = new Dialog(context,R.style.Theme_Dialog2);
                        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog1.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        dialog1.setContentView(R.layout.popup);
                        type_listView=(ListView)dialog1.findViewById(R.id.List_popup);

                        get_type();
                        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
                        dialog1.show();

                        type_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                Type=list_type.get(position).get_TypeName();
                                Toast.makeText(getApplicationContext(),"You clicked"+Type,Toast.LENGTH_SHORT).show();
                                dialog1.dismiss();
                                type_txt.setText(Type);
                            }
                        });
                    }
                });

                additional_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        ImageView tick=(ImageView)view.findViewById(R.id.tick);
                        String item=FilterList.get(position).getName();
                        if (tick.getVisibility() == View.VISIBLE)
                        {
                            Log.d("Availibity","Yes");
                            tick.setVisibility(View.GONE);
                            Item1.remove(FilterList.get(position).getName());
                        }
                        else if(tick.getVisibility()==View.GONE)
                        {
                            Log.d("Availibility","No");
                            tick.setVisibility(View.VISIBLE);
                            Item1.add(FilterList.get(position).getName());
                        }
                    }
                });


                Button done=(Button)dialog.findViewById(R.id.done_filter);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Log.i("ItemList",Item1.toString());
                        //Item1.clear();
                    }
                });
            }
        });
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }

    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(), BottomTabs.class);
        startActivity(i);
    }


    public void getData()
    {
        String food_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/2/1/vendors?lang=";
        String food_urlA=food_url+LanGet+"&page=1&limit=10";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, food_urlA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            if(response1.equals("[]"))
                            {
                                Result.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }

                            else {
                                JSONArray jsonArray = new JSONArray(response1);
                                Log.i("Response", jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    adapter = new FoodAdapter2(AttractionActivity.this, list);
                                    listView.setAdapter(adapter);
                                    JSONObject actor = jsonArray.getJSONObject(i);
                                    FoodModel2 dataSet = new FoodModel2();
                                    dataSet.setVendor_id(actor.getString("vendor_id"));
                                    dataSet.setCategory_id(actor.getString("category_id"));
                                    dataSet.setSub_category_id(actor.getString("sub_category_id"));
                                    dataSet.setVendor_name(actor.getString("vendor_name"));
                                    dataSet.setVendor_avatar(actor.getString("vendor_avatar"));
                                    dataSet.setLat(actor.getString("lat"));
                                    dataSet.setLng(actor.getString("long"));
                                    dataSet.setAddress(actor.getString("address"));
                                    dataSet.setDistance(actor.getString("distance"));
                                    list.add(dataSet);
                                }

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        String vendor_id = list.get(position).getVendor_id();
                                        String vendor_name = list.get(position).getVendor_name();
                                        PrefManager aa = new PrefManager(getApplicationContext());
                                        aa.setVendorId(vendor_id);
                                        aa.setVendorName(vendor_name);
                                        Intent i = new Intent(getApplicationContext(), FoodDetail2.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("Activity", "Attraction");
                                        i.putExtras(bundle);
                                        startActivity(i);
                                        overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                    }
                                });
                                progressBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AttractionActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization", token);
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }

        };
        MySingleton.getmInstance(AttractionActivity.this).addToRequestque(stringRequest);
    }

    public void get_type()
    {
        String type_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor_types/2?lang="+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, type_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            list_type.clear();
                            type_adapter=new TypeAdapter(AttractionActivity.this,list_type);
                            type_listView.setAdapter(type_adapter);
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.d("TypeResponse",response1);
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                TypeModel typeModel=new TypeModel();
                                JSONObject actor = jsonArray.getJSONObject(i);
                                typeModel.set_TypeId(actor.getString("id"));
                                typeModel.set_TypeName(actor.getString("name"));
                                String name=actor.getString("name");
                                Log.d("TypeResponse",name);
                                list_type.add(typeModel);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AttractionActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization", token);
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        };

        MySingleton.getmInstance(AttractionActivity.this).addToRequestque(stringRequest);
    }


    public void get_Additional_Details()
    {
        String type_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor_additional_details/2?lang="+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, type_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            FilterList.clear();
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.d("TypeResponse",response1);
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                filterAdapter=new FilterAdapter(AttractionActivity.this,FilterList);
                                additional_list.setAdapter(filterAdapter);
                                FilterModel filterModel=new FilterModel();
                                JSONObject actor = jsonArray.getJSONObject(i);
                                filterModel.setId(actor.getInt("id"));
                                filterModel.setName(actor.getString("name"));
                                String name=actor.getString("name");
                                Log.d("TypeResponse",name);
                                FilterList.add(filterModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AttractionActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type","application/json");
                headers.put("Authorization", token);
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        };

        MySingleton.getmInstance(AttractionActivity.this).addToRequestque(stringRequest);
    }


    public void initViews2()
    {
        listView=(ListView)findViewById(R.id.listview);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        Back=(ImageView)findViewById(R.id.back);
        Map=(TextView)findViewById(R.id.MapText);
        Filter=(ImageView)findViewById(R.id.filter);
        editsearch=(EditText)findViewById(R.id.search);
        relativeLayout=(RelativeLayout)findViewById(R.id.main);
        FoodTxt=(TextView)findViewById(R.id.FoodText);
        Result=(TextView)findViewById(R.id.result);
    }


    public void preferences()
    {
        PrefManager aa=new PrefManager(this);
        token=aa.getToken().toString();
        LanGet=aa.getLanguage();
        if(LanGet.equals("en"))
        {
            relativeLayout.setLayoutDirection(LTR);
            FoodTxt.setText("Attractions & Leisure");
            Map.setText("MAP");
        }
        else if(LanGet.equals("ar"))
        {
            relativeLayout.setLayoutDirection(RTL);
            FoodTxt.setText("الجذب السياحي و ليسوريس");
            Map.setText("خرائط");
        }
    }


}
