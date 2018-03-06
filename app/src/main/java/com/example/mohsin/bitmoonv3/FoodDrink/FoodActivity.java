package com.example.mohsin.bitmoonv3.FoodDrink;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.Home.HomeActivity;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.Login.NewTest;
import com.example.mohsin.bitmoonv3.MainActivity;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.Category_Model;
import com.example.mohsin.bitmoonv3.models.CuisineModel;
import com.example.mohsin.bitmoonv3.models.CurrencyModel;
import com.example.mohsin.bitmoonv3.models.FilterModel;
import com.example.mohsin.bitmoonv3.models.FoodModel;
import com.example.mohsin.bitmoonv3.models.FoodModel2;
import com.example.mohsin.bitmoonv3.models.TypeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;
import static com.example.mohsin.bitmoonv3.R.attr.theme;
import static com.example.mohsin.bitmoonv3.R.attr.windowFixedWidthMinor;

public class FoodActivity extends AppCompatActivity {
    /* Main Food Listing Attributes */
    private ListView listView;
    private List<FoodModel2>list=new ArrayList<FoodModel2>();
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
    View ChildView;
    int RecyclerViewItemPosition ;
    String Base_Url;

    /* Horizontal Recycler View Filter Attributes */
    final ArrayList<Category_Model> arrayList = new ArrayList<>();
    public static final String[] TITLES= {"All","Arabic","Pizza","Burger","Chinese"};
    public static final Integer[] IMAGES= {R.drawable.all_picture,R.drawable.arabic_picture,R.drawable.burger,R.drawable.pizza,
            R.drawable.chinese};
    public static final String[] SubCatId={"1","2","3","4","5"};
    private static RecyclerView recyclerView;
    private FoodRecyclerAdapter adapter2;

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
    private List<String>TypeItem=new ArrayList<String>();

    JSONArray TypeArray;
    JSONArray CuisineArray;
    JSONArray AddItem;
    String TypeString,CuisineString,AdditionalString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_food);
        initViews2();
        preferences();
        Base_Url=getString(R.string.Base_Url);

        Result.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        if(TypeString.isEmpty() && CuisineString.isEmpty() && AdditionalString.isEmpty())
        {
            getData2("1");
        }
        else
        {
            try {
                TypeArray=new JSONArray(TypeString);
                CuisineArray=new JSONArray(CuisineString);
                AddItem=new JSONArray(AdditionalString);

                get_Filter_Data();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initViews();
        populatRecyclerView();

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
                Intent i=new Intent(FoodActivity.this,FoodMaps.class);
                Bundle bundle=new Bundle();
                bundle.putString("Category","1");
                i.putExtras(bundle);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    RecyclerViewItemPosition = Recyclerview.getChildAdapterPosition(ChildView);
                    String ar=arrayList.get(RecyclerViewItemPosition).getSubCatId();
                    Log.i("VendorName",ar);
                    list.clear();
                    listView.setAdapter(null);
                    getData2(ar);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        final List<Integer>Item100=new ArrayList<Integer>();
        Filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 final Dialog dialog = new Dialog(context,R.style.Theme_Dialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                dialog.setContentView(R.layout.custom_filer);
                dialog.setTitle("Title...");
                additional_list=(ListView)dialog.findViewById(R.id.lista);

                TextView Type_Txt=(TextView)dialog.findViewById(R.id.textView5);
                TextView Type_Button_Txt=(TextView) dialog.findViewById(R.id.textView4);
                TextView Cuisine_Txt=(TextView)dialog.findViewById(R.id.textView8);
                TextView Cuisine_Button_Txt=(TextView) dialog.findViewById(R.id.textView9);
                TextView Additional_Txt=(TextView)dialog.findViewById(R.id.textView2);
                RelativeLayout filter_relative=(RelativeLayout)dialog.findViewById(R.id.filter_main_layout);
                TextView FilterTxt=(TextView)dialog.findViewById(R.id.FilterText);

                if(LanGet.equals("en"))
                {
                    Type_Txt.setText("Type");
                    Type_Button_Txt.setText("SELECT TYPE");
                    Cuisine_Txt.setText("Cuisine");
                    Cuisine_Button_Txt.setText("SELECT CUISINE");
                    Additional_Txt.setText("ADDITIONAL DETAILS");
                    filter_relative.setLayoutDirection(LTR);
                    FilterTxt.setText("FILTERS");
                }
                else if(LanGet.equals("ar"))
                {
                    Type_Txt.setText("اكتب");
                    Type_Button_Txt.setText("اختر صنف");
                    Cuisine_Txt.setText("أطباق");
                    Cuisine_Button_Txt.setText("حدد المطبخ");
                    Additional_Txt.setText("تفاصيل اضافية");
                    filter_relative.setLayoutDirection(RTL);
                    FilterTxt.setText("مرشحات");
                }
                get_Additional_Details();

                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                dialog.show();

                final TextView type_txt=(TextView)dialog.findViewById(R.id.type_txt);
                final Button cuisine=(Button)dialog.findViewById(R.id.cuisine);
                cuisine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cuisine_dialog=new Dialog(context,R.style.Theme_Dialog2);
                        cuisine_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        cuisine_dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
                        cuisine_dialog.setContentView(R.layout.cuisine_popup);
                        cuisine_listView=(ListView)cuisine_dialog.findViewById(R.id.List_popup);
                        get_cuisine();
                        cuisine_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
                        cuisine_dialog.show();

                        Done1=(TextView) cuisine_dialog.findViewById(R.id.done);
                        Done1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cuisine_dialog.dismiss();
                                Log.i("ItemList",CuisineItem.toString());
                                TextView cuisine_txt=(TextView)dialog.findViewById(R.id.cuisine_txt);
                                cuisine_txt.setText(CuisineItem.toString());
                            }
                        });
                        cuisine_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                ImageView tick=(ImageView)view.findViewById(R.id.tick);
                                String cuisine=list_cuisine.get(position).getId();
                                if (tick.getVisibility() == View.VISIBLE)
                                {
                                    Log.d("Availibity","Yes");
                                    tick.setVisibility(View.GONE);
                                    CuisineItem.remove(list_cuisine.get(position).getId());
                                }
                                else if(tick.getVisibility()==View.GONE)
                                {
                                    Log.d("Availibility","No");
                                    tick.setVisibility(View.VISIBLE);
                                    CuisineItem.add(list_cuisine.get(position).getId());
                                }
                            }
                        });
                    }
                });

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
                                TypeItem.add(list_type.get(position).get_TypeId());
                                Toast.makeText(getApplicationContext(),"You clicked"+TypeItem,Toast.LENGTH_SHORT).show();
                                dialog1.dismiss();
                                Type=TypeItem.toString();
                                type_txt.setText(TypeItem.toString());
                            }
                        });
                    }
                });
                for(int i=0;i<Item1.size();i++)
                {
                    Log.i("ItemType",Item1.get(i));
                }
                additional_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        ImageView tick=(ImageView)view.findViewById(R.id.tick);
                        if (tick.getVisibility() == View.VISIBLE)
                        {
                            Log.d("Availibity","Yes");
                            tick.setVisibility(View.GONE);
                            Item1.remove(FilterList.get(position).getId());
                        }
                        else if(tick.getVisibility()==View.GONE)
                        {
                            Log.d("Availibility","No");
                            tick.setVisibility(View.VISIBLE);
                            Item1.add(String.valueOf(FilterList.get(position).getId()));
                            Item100.add(position);
                        }
                    }
                });
                Button done=(Button)dialog.findViewById(R.id.done_filter);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            TypeString=TypeItem.toString();
                            CuisineString=CuisineItem.toString();
                            AdditionalString=Item1.toString();
                           // Log.d("Additional",Item1.toString());
                            PrefManager prefManager=new PrefManager(getApplicationContext());
                            prefManager.setTypeArray(TypeString);
                            prefManager.setCuisineArray(CuisineString);
                            prefManager.setAdditionalArray(AdditionalString);
                            TypeArray=new JSONArray(TypeString);
                            CuisineArray=new JSONArray(CuisineString);
                            AddItem=new JSONArray(AdditionalString);
                            get_Filter_Data();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        filterAdapter.func(Item100);
                        Log.i("ItemList",Item1.toString());
                    }
                });

                Button clear=(Button)dialog.findViewById(R.id.clear_filter);
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filterAdapter.func(Item100);
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

    // Horizontal Recycler View //
    private void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Set Back Icon on Activity
        recyclerView = (RecyclerView) findViewById(R.id.filters);
        recyclerView.setHasFixedSize(true);

        getSupportActionBar().setTitle("Horizontal Recycler View");
        recyclerView.setLayoutManager(new LinearLayoutManager(FoodActivity.this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void populatRecyclerView() {
        for (int i = 0; i < TITLES.length; i++) {
            arrayList.add(new Category_Model(TITLES[i],IMAGES[i],SubCatId[i]));
        }
        adapter2 = new FoodRecyclerAdapter(FoodActivity.this, arrayList);
        recyclerView.setAdapter(adapter2);

           /* String food_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/sub_categories/1?lang=";
            String food_urlA=food_url+"en";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, food_urlA,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                adapter2 = new FoodRecyclerAdapter(FoodActivity.this, arrayList);
                                recyclerView.setAdapter(adapter2);
                                JSONObject jsonObject=new JSONObject(response);
                                String response1=jsonObject.getString("response");
                                Log.i("ResponseSub",response1);
                                JSONArray jsonArray = new JSONArray(response1);
                                Log.i("Response", jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject actor = jsonArray.getJSONObject(i);
                                    Category_Model dataSet = new Category_Model();
                                    dataSet.setImage(actor.getString("avatar"));
                                    dataSet.setName(actor.getString("sub_category_name_en"));
                                    arrayList.add(dataSet);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(FoodActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
            MySingleton.getmInstance(FoodActivity.this).addToRequestque(stringRequest);*/
        }

    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(), BottomTabs.class);
        startActivity(i);
    }


    public void getData()
    {
        String food_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/1/1/vendors?lang=";
        String food_urlA=food_url+"en"+"&page=1&limit=10";
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
                                    list.clear();
                                    JSONArray jsonArray = new JSONArray(response1);
                                    Log.i("Response", jsonArray.toString());
                                    for (int i = 0; i < jsonArray.length(); i++)
                                    {
                                        adapter = new FoodAdapter2(FoodActivity.this, list);
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
                                            String Lat=list.get(position).getLat();
                                            String Lng=list.get(position).getLng();
                                            PrefManager aa = new PrefManager(getApplicationContext());
                                            aa.setVendorId(vendor_id);
                                            aa.setVendorName(vendor_name);
                                            /*aa.setLat(Lat);
                                            aa.setLng(Lng);*/
                                            Intent i = new Intent(getApplicationContext(), FoodDetail2.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("Activity", "Food");
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
                Toast.makeText(FoodActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(FoodActivity.this).addToRequestque(stringRequest);
    }


    public void getData2(String SubCatId)
    {
        String SubId;
        SubId=SubCatId;
        String food_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/1/"+SubId+"/vendors?lang=";
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
                                list.clear();
                                Result.setVisibility(View.GONE);
                                JSONArray jsonArray = new JSONArray(response1);
                                Log.i("Response", jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    adapter = new FoodAdapter2(FoodActivity.this, list);
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
                                        String Lat=list.get(position).getLat();
                                        String Lng=list.get(position).getLng();
                                        PrefManager aa = new PrefManager(getApplicationContext());
                                        aa.setVendorId(vendor_id);
                                        aa.setVendorName(vendor_name);
                                        Intent i = new Intent(getApplicationContext(), FoodDetail2.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("Activity", "Food");
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
                Toast.makeText(FoodActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(FoodActivity.this).addToRequestque(stringRequest);
    }

    public void get_type()
    {
        String type_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor_types/1?lang="+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, type_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            list_type.clear();
                            type_adapter=new TypeAdapter(FoodActivity.this,list_type);
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
                Toast.makeText(FoodActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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

        MySingleton.getmInstance(FoodActivity.this).addToRequestque(stringRequest);
    }



    public void get_cuisine()
    {
        String type_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor_cuisine/1?lang="+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, type_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            list_cuisine.clear();
                            cuisineAdapter=new CuisineAdapter(FoodActivity.this,list_cuisine);
                            cuisine_listView.setAdapter(cuisineAdapter);
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.d("TypeResponse",response1);
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                CuisineModel cuisineModel=new CuisineModel();
                                JSONObject actor = jsonArray.getJSONObject(i);
                                cuisineModel.setId(actor.getString("id"));
                                cuisineModel.setName(actor.getString("name"));
                                String name=actor.getString("name");
                                Log.d("TypeResponse",name);
                                list_cuisine.add(cuisineModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FoodActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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

        MySingleton.getmInstance(FoodActivity.this).addToRequestque(stringRequest);
    }


    public void get_Additional_Details()
    {
        String type_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor_additional_details/1?lang="+LanGet;
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
                                filterAdapter=new FilterAdapter(FoodActivity.this,FilterList);
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
                Toast.makeText(FoodActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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

        MySingleton.getmInstance(FoodActivity.this).addToRequestque(stringRequest);
    }

    public void get_Filter_Data(){
        String url2="http://www.appstudeo.com/bitmoonbackend/public/index.php/api/user/1/vendors?lang="+LanGet+"&page=1&limit=1000";
        AdditionalString=AdditionalString.replace("[", "").replace("]", "");
        CuisineString=CuisineString.replace("[", "").replace("]", "");
        TypeString=TypeString.replace("[", "").replace("]", "");
        TypeItem.add(TypeString);
        CuisineItem.add(CuisineString);
        Item1.add(AdditionalString);
        Log.i("TypeItem", Item1.toString());
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject params = new JSONObject();
            params.put("type", TypeArray);
            params.put("cuisine",CuisineArray);
            params.put("add_details",AddItem);


            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String response1=jsonObject.getString("response");
                        Log.i("Response2217",response1);
                        listView.setAdapter(null);
                        if(response1.equals("[]"))
                        {
                            Result.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            list.clear();
                            JSONArray jsonArray = new JSONArray(response1);
                            Log.i("Response", jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                adapter = new FoodAdapter2(FoodActivity.this, list);
                                listView.setAdapter(adapter);
                                JSONObject actor = jsonArray.getJSONObject(i);
                                FoodModel2 dataSet2 = new FoodModel2();
                                dataSet2.setVendor_id(actor.getString("id"));
                                //dataSet.setCategory_id(actor.getString("category_id"));
                                //dataSet.setSub_category_id(actor.getString("sub_category_id"));
                                dataSet2.setVendor_name(actor.getString("vendor_name"));
                                dataSet2.setVendor_avatar(actor.getString("vendor_avatar"));
                                dataSet2.setLat(actor.getString("lat"));
                                dataSet2.setLng(actor.getString("long"));
                                dataSet2.setAddress(actor.getString("address"));
                                dataSet2.setDistance(actor.getString("distance"));
                                list.add(dataSet2);
                                Log.d("ListData", String.valueOf(list));
                            }
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    String vendor_id = list.get(position).getVendor_id();
                                    String vendor_name = list.get(position).getVendor_name();
                                    String Lat=list.get(position).getLat();
                                    String Lng=list.get(position).getLng();
                                    PrefManager aa = new PrefManager(getApplicationContext());
                                    aa.setVendorId(vendor_id);
                                    aa.setVendorName(vendor_name);
                                    Intent i = new Intent(getApplicationContext(), FoodDetail2.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Activity", "Food");
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
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        Log.d("Req", "getBody: "+mRequestBody);
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("client-id", "bitmoon-app-ios");
                    headers.put("Authorization", token);
                    headers.put("Content-Type","application/json");
                    return headers;
                }
            };
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        JustShow=(TextView)findViewById(R.id.justShow);
        Result=(TextView)findViewById(R.id.result);
    }


    public void preferences()
    {
        PrefManager aa=new PrefManager(this);
        token=aa.getToken().toString();
        LanGet=aa.getLanguage();
        TypeString=aa.getTypeArray();
        CuisineString=aa.getCuisineArray();
        AdditionalString=aa.getAdditionalArray();
        Log.d("PreferenceData",TypeString);
        Log.d("PreferenceData",CuisineString);
        Log.d("PreferenceData",AdditionalString);


        if(LanGet.equals("en"))
        {
            relativeLayout.setLayoutDirection(LTR);
            FoodTxt.setText("Food & Drinks");
            Map.setText("MAP");
            JustShow.setText("Just Show Me");
        }
        else if(LanGet.equals("ar"))
        {
            relativeLayout.setLayoutDirection(RTL);
            FoodTxt.setText("المأكولات والمشروبات");
            Map.setText("خرائط");
            JustShow.setText("فقط تبين لي");
        }
    }


}
