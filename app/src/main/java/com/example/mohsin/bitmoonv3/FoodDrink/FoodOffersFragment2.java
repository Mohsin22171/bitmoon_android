package com.example.mohsin.bitmoonv3.FoodDrink;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
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
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mohsin.bitmoonv3.*;
import com.example.mohsin.bitmoonv3.CustomerProfile.CustomerProfileActivity;
import com.example.mohsin.bitmoonv3.CustomerProfile.Purchase_History_Adapter;
import com.example.mohsin.bitmoonv3.CustomerProfile.RuleAdapter;
import com.example.mohsin.bitmoonv3.CustomerProfile.RuleOfUse;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.Login.NewTest;
import com.example.mohsin.bitmoonv3.Login.Register;
import com.example.mohsin.bitmoonv3.PurchaseNew.PurchaseActivity2;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.AddressModel;
import com.example.mohsin.bitmoonv3.models.BranchModel;
import com.example.mohsin.bitmoonv3.models.FoodModel;
import com.example.mohsin.bitmoonv3.models.FoodModel2;
import com.example.mohsin.bitmoonv3.models.FoodOffers;
import com.example.mohsin.bitmoonv3.models.FoodOffers2;
import com.example.mohsin.bitmoonv3.models.RuleModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;
import static com.example.mohsin.bitmoonv3.R.anim.slide_down;
import static com.example.mohsin.bitmoonv3.R.drawable.rec1;
import static com.example.mohsin.bitmoonv3.R.id.city_text;
import static com.example.mohsin.bitmoonv3.R.id.fill;
import static com.example.mohsin.bitmoonv3.R.id.progress;
import static com.example.mohsin.bitmoonv3.R.id.view;

public class FoodOffersFragment2 extends Fragment {

    String token,vendor_id,vendor_avatar,vendor_background_image,vendor_name;
    ImageView ImageHeader,HeaderIcon;
    TextView address1,Loc,Title,offersTxt1,offersTxt2;
    ImageView Icon,Share,Logo,Logo2;
    TextView offer_name,offer_desc,estimated_savings,offer_name2,redemption_txt;

    private List<FoodOffers2> list = new ArrayList<FoodOffers2>();
    private ListView listView;
    private FoodOffersAdapter2 adapter;

    private ListView branch_listView;
    private BranchAdapter branch_adapter;
    private List<BranchModel> branch_list = new ArrayList<BranchModel>();
    String Offer_Name;

    ProgressBar progressBar;
    String city_id,LanGet,city_vendor,vendor_city;
    RelativeLayout relativeLayout;
    String aaaa,bbbb,cccc,dddd,user_pin;
    RelativeLayout mainRelative;
    RelativeLayout inner3;
    EditText edtxt1,edtxt2,edtxt3,edtxt4;
    String user="1";
    Dialog dialog;
    int limit=0;
    int flag=1;
    AlertDialog.Builder builder;

    // Rule Attributes //
    private ListView RuleListView;
    private List<RuleModel>rule_list=new ArrayList<RuleModel>();
    private RuleAdapter rule_adapter;
    ImageView Cross;
    String key_check,branch_id,limit_check;
    String get_branch_id,offer_id;
    String Base_Url;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_food_offers2, container, false);
        Base_Url=getString(R.string.Base_Url);
        // Init View //
        ImageHeader=(ImageView)view.findViewById(R.id.ImageHeader);
        HeaderIcon=(ImageView)view.findViewById(R.id.HeaderIcon);
        Icon=(ImageView)view.findViewById(R.id.Icon);
        Share=(ImageView)view.findViewById(R.id.Share);
        listView=(ListView)view.findViewById(R.id.food_offers_list);
        Share=(ImageView)view.findViewById(R.id.Share);
        relativeLayout=(RelativeLayout)view.findViewById(R.id.main);

        // Preference //
        final PrefManager aa=new PrefManager(getActivity());
        token=aa.getToken().toString();
        vendor_id=aa.getvendorId().toString();
        vendor_name=aa.getvendorName();
        city_id=aa.getCityId();
        LanGet=aa.getLanguage();
        city_vendor=aa.getvendorCity();
        get_branch_id=aa.getBranchId();
        Log.i("Branch_id",vendor_id);
        Log.i("Branch_id",get_branch_id);
        if(LanGet.equals("en"))
        {
            relativeLayout.setLayoutDirection(LTR);
        }
        else if(LanGet.equals("ar"))
        {
            relativeLayout.setLayoutDirection(RTL);
        }

        Icon.setVisibility(View.GONE);
        Share.setVisibility(View.GONE);
        if(get_branch_id.isEmpty())
        {
            Location_Dialog();
        }
        else
        {

        }
        getData();
        get_check_pin();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.food_offers_header,listView,false);
        listView.addHeaderView(header);
        address1=(TextView)header.findViewById(R.id.address);
        offersTxt1=(TextView)header.findViewById(R.id.offers_txt);
        offersTxt2=(TextView)header.findViewById(R.id.offers_txt2);
        String city_vendorA=vendor_city+"In Product 2017";


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                position -= listView.getHeaderViewsCount();
                offer_id=list.get(position).getOffer_id();
                Log.i("OfferID",offer_id);
                get_Offer_Detail();

                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.redeem_code_dialog);
                dialog.setCancelable(false);

                // Dialog Data //
                FoodOffers2 m=list.get(position);
                offer_name=dialog.findViewById(R.id.text_head);
                //offer_name.setText(m.getOffer_name());
                Logo=dialog.findViewById(R.id.logo);
                Picasso.with(getActivity()).load(vendor_avatar).into(Logo);
                offer_desc=(TextView)dialog.findViewById(R.id.text_body);
                //offer_desc.setText(m.getOffer_detail());
                estimated_savings=(TextView)dialog.findViewById(R.id.usd);
                //estimated_savings.setText("USD "+m.getOffer_saving_price());
                Logo2=(ImageView)dialog.findViewById(R.id.logo2);
                Picasso.with(getActivity()).load(vendor_avatar).into(Logo2);
                offer_name2=(TextView)dialog.findViewById(R.id.text_head2);
                //offer_name2.setText(m.getOffer_name());
                redemption_txt=(TextView)dialog.findViewById(R.id.redemption_txt);


                mainRelative=(RelativeLayout)dialog.findViewById(R.id.rl);
                final RelativeLayout inner2=(RelativeLayout)dialog.findViewById(R.id.inner_dialog2);
                inner2.setVisibility(view.GONE);
                inner3=(RelativeLayout)dialog.findViewById(R.id.inner_dialog3);
                inner3.setVisibility(view.GONE);
                final RelativeLayout inner=(RelativeLayout)dialog.findViewById(R.id.inner_dialog);
                ImageView Cancel=(ImageView)dialog.findViewById(R.id.cancel);
                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flag=1;
                        user="1";
                        dialog.dismiss();
                    }
                });

                TextView RuleTxt=(TextView)dialog.findViewById(R.id.rule_txt);
                RuleTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog rule_dialog = new Dialog(getActivity(),R.style.FullDialog);
                        rule_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        rule_dialog.setCancelable(true);
                        view  = getActivity().getLayoutInflater().inflate(R.layout.activity_rule, null);
                        rule_dialog.setContentView(view);
                        RuleListView=(ListView)rule_dialog.findViewById(R.id.ListView);
                        getRule();

                        Cross=(ImageView)rule_dialog.findViewById(R.id.cross);
                        Cross.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                rule_dialog.dismiss();
                            }
                        });
                        rule_dialog.show();
                    }
                });

                Button Redeem=(Button)dialog.findViewById(R.id.redeem);
                Redeem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        inner.setVisibility(view.GONE);
                        inner2.setVisibility(view.VISIBLE);
                        inner3.setVisibility(view.VISIBLE);

                        edtxt1 = (EditText) dialog.findViewById(R.id.digit1);
                        edtxt2 = (EditText) dialog.findViewById(R.id.digit2);
                        edtxt3 = (EditText) dialog.findViewById(R.id.digit3);
                        edtxt4 = (EditText) dialog.findViewById(R.id.digit4);

                        edtxt1.addTextChangedListener(new TextWatcher() {
                            public void afterTextChanged(Editable s) {
                                if (s.length() ==1) {
                                    edtxt2.requestFocus();
                                }
                            }

                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before,
                                                      int count) {
                                aaaa=edtxt1.getText().toString();
                                Log.i("aa",aaaa);
                            }
                        });

                        edtxt2.addTextChangedListener(new TextWatcher() {
                            public void afterTextChanged(Editable s) {
                                if (s.length() == 1) {
                                    edtxt3.requestFocus();
                                }

                            }

                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {

                            }

                            public void onTextChanged(CharSequence s, int start, int before,
                                                      int count) {
                                bbbb=edtxt2.getText().toString();
                                Log.i("aa",bbbb);

                            }
                        });
                        edtxt3.addTextChangedListener(new TextWatcher() {
                            public void afterTextChanged(Editable s) {
                                if (s.length() == 1) {
                                    edtxt4.requestFocus();
                                }

                            }

                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before,
                                                      int count) {
                                cccc=edtxt3.getText().toString();
                                Log.i("aa",cccc);
                            }
                        });
                        edtxt4.addTextChangedListener(new TextWatcher() {
                            public void afterTextChanged(Editable s) {
                                if (s.length() == 1) {
                                }
                            }

                            public void beforeTextChanged(CharSequence s, int start, int count,
                                                          int after) {
                            }

                            public void onTextChanged(CharSequence s, int start, int before,
                                                      int count) {
                                dddd=edtxt4.getText().toString();
                                Log.i("aa",dddd);
                                user_pin=aaaa+bbbb+cccc+dddd;
                                Log.i("aa",user_pin);
                                if(user.equals("1"))   //User Redeem Pin //
                                {
                                    getPin();
                                }
                                else if(user.equals("2"))
                                {
                                    flag=1;
                                    getPin_Vendor();
                                }
                            }
                        });
                    }
                });
                if(key_check!=null)
                {
                    Log.i("Key","Not Null");
                    get_offer_limit();
                }
                else
                {
                    Log.i("Key","Null");
                    //Toast.makeText(getActivity(),"Please Create Your Pin",Toast.LENGTH_SHORT).show();
                    displayAlert("CreatePin");
                }
            }
        });

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Loc=(TextView)header.findViewById(R.id.loc_txt);
        Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location_Dialog();
            }
        });
        return view;
    }

    public void Location_Dialog(){
        getBranches();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        View view  = getActivity().getLayoutInflater().inflate(R.layout.custom_restaurant_location_popup, null);
        dialog.setContentView(view);
        Title=(TextView)dialog.findViewById(R.id.title);
        branch_listView=(ListView)dialog.findViewById(R.id.lista);
        progressBar=(ProgressBar)dialog.findViewById(R.id.progress);
        Title.setText(vendor_name);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branch_listView.setAdapter(null);
                dialog.dismiss();
            }
        });
        progressBar.setVisibility(View.VISIBLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        dialog.show();
    }

    public void getBranches() {
        String branch_url = Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor/branches?vendor_id=";
        MySingleton.getmInstance(getActivity()).addToRequestque(new StringRequest(0, (Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor/branches?vendor_id=" + this.vendor_id + "&lang=") + this.LanGet, new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    FoodOffersFragment2.this.branch_list.clear();
                    JSONObject jsonObject1 = new JSONObject(new JSONObject(response).getString("response"));
                    String branches = jsonObject1.getString("branches");
                    Log.i("Response", jsonObject1.getString("vendor_name"));
                    if (branches.equals("[]")) {
                        FoodOffersFragment2.this.progressBar.setVisibility(View.GONE);
                        return;
                    }
                        JSONArray jsonArray = new JSONArray(branches);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            FoodOffersFragment2.this.branch_adapter = new BranchAdapter(FoodOffersFragment2.this.getActivity(), FoodOffersFragment2.this.branch_list);
                            FoodOffersFragment2.this.branch_listView.setAdapter(FoodOffersFragment2.this.branch_adapter);
                            BranchModel branchModel = new BranchModel();
                            JSONObject actor = jsonArray.getJSONObject(i);
                            String branch_id = actor.getString("branch_name");
                            branchModel.setBranch_id(actor.getString("branch_id"));
                            branchModel.setBranch_Name(actor.getString("branch_name"));
                            branchModel.setLat(actor.getString("lat"));
                            branchModel.setLng(actor.getString("long"));
                            FoodOffersFragment2.this.branch_list.add(branchModel);
                        }
                        if (FoodOffersFragment2.this.get_branch_id.isEmpty()) {
                            FoodOffersFragment2.this.get_branch_id = ((BranchModel) FoodOffersFragment2.this.branch_list.get(0)).getBranch_id();
                            PrefManager prefManager = new PrefManager(FoodOffersFragment2.this.getActivity());
                            prefManager.setLat(((BranchModel) FoodOffersFragment2.this.branch_list.get(0)).getLat());
                            prefManager.setLng(((BranchModel) FoodOffersFragment2.this.branch_list.get(0)).getLng());
                            String BranchName = ((BranchModel) FoodOffersFragment2.this.branch_list.get(0)).getBranch_Name();
                            prefManager.setVendorAddress(BranchName);
                            FoodOffersFragment2.this.getData();
                        }
                        FoodOffersFragment2.this.branch_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                String Lat = ((BranchModel) FoodOffersFragment2.this.branch_list.get(position)).getLat();
                                String Lng = ((BranchModel) FoodOffersFragment2.this.branch_list.get(position)).getLng();
                                String BranchName = ((BranchModel) FoodOffersFragment2.this.branch_list.get(position)).getBranch_Name();
                                FoodOffersFragment2.this.branch_id = ((BranchModel) FoodOffersFragment2.this.branch_list.get(position)).getBranch_id();
                                Log.i("BranchId", FoodOffersFragment2.this.branch_id);
                                PrefManager prefManager = new PrefManager(FoodOffersFragment2.this.getActivity());
                                prefManager.setLat(Lat);
                                prefManager.setLng(Lng);
                                prefManager.setBranchId(FoodOffersFragment2.this.branch_id);
                                prefManager.setVendorAddress(BranchName);
                                String Act=prefManager.getActivity_1();
                                Intent i = new Intent(FoodOffersFragment2.this.getActivity(), FoodDetail2.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("Activity",Act);
                                i.putExtras(bundle);
                                FoodOffersFragment2.this.startActivity(i);
                            }
                        });
                        FoodOffersFragment2.this.progressBar.setVisibility(8);
                        FoodOffersFragment2.this.branch_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FoodOffersFragment2.this.getActivity(), "No Internet Connection", 1).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("Content-type", "application/json");
                headers.put("Authorization", FoodOffersFragment2.this.token);
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        });
    }

    /*public void getBranches() {
        final String branch_url = "http://54.187.13.62/bitmoonbackend/public/index.php/api/user/vendor/branches?vendor_id=";
        String branch_urlA=branch_url+vendor_id+"&lang=";
        //String branch_urlB=branch_url+city_id+"&lang=";
        String branch_urlC=branch_urlA+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, branch_urlC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            branch_list.clear();
                            JSONObject jsonObject = new JSONObject(response);
                            String response1 = jsonObject.getString("response");
                            JSONObject jsonObject1 = new JSONObject(response1);
                            String branches = jsonObject1.getString("branches");
                            String vendor_name=jsonObject1.getString("vendor_name");
                            Log.i("Response",vendor_name);
                            if(branches.equals("[]"))
                            {
                                progressBar.setVisibility(View.GONE);
                            }
                            else
                            {
                            JSONArray jsonArray = new JSONArray(branches);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                branch_adapter = new BranchAdapter(getActivity(), branch_list);
                                branch_listView.setAdapter(branch_adapter);
                                BranchModel branchModel = new BranchModel();
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String branch_id = actor.getString("branch_name");
                                branchModel.setBranch_id(actor.getString("branch_id"));
                                branchModel.setBranch_Name(actor.getString("branch_name"));
                                branchModel.setLat(actor.getString("lat"));
                                branchModel.setLng(actor.getString("long"));
                                branch_list.add(branchModel);
                            }
                                if(get_branch_id.isEmpty()) {
                                    get_branch_id = branch_list.get(0).getBranch_id();
                                    PrefManager prefManager = new PrefManager(getActivity());
                                    prefManager.setLat(branch_list.get(0).getLat());
                                    prefManager.setLng(branch_list.get(0).getLng());
                                    getData();
                                }
                            branch_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    String Lat=branch_list.get(position).getLat();
                                    String Lng=branch_list.get(position).getLng();
                                    branch_id=branch_list.get(position).getBranch_id();
                                    Log.i("BranchId",branch_id);
                                    PrefManager prefManager=new PrefManager(getActivity());
                                    prefManager.setLat(Lat);
                                    prefManager.setLng(Lng);
                                    prefManager.setBranchId(branch_id);
                                    //dialog.dismiss();
                                    // dialog.dismiss();
                                    Intent i=new Intent(getActivity(),FoodDetail2.class);
                                    Bundle bundle=new Bundle();
                                    bundle.putString("Activity","Food");
                                    i.putExtras(bundle);
                                    startActivity(i);
                                }
                            });
                                progressBar.setVisibility(View.GONE);
                                branch_adapter.notifyDataSetChanged();
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                Log.e("error is ", "" + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-type", "application/json");
                headers.put("Authorization", token);
                headers.put("client-id", "bitmoon-app-android");
                return headers;
            }
        };
        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);
    }*/


    public void getData()
    {
        String url=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor/offers/";
        String url2=url+vendor_id+"/"+get_branch_id+"?lang=";
        String url3=url2+LanGet;
        Log.d("Response2217",url3);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url3,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            JSONObject jsonObject1=new JSONObject(response1);
                            if(response1.equals("[]"))
                            {

                            }
                            else {
                                Log.d("Mohsin2217",response);
                                String vendor_id = jsonObject1.getString("vendor_id");
                                String vendor_name = jsonObject1.getString("vendor_name");
                                final String phone = jsonObject1.getString("phone");
                                String lat = jsonObject1.getString("lat");
                                String lng = jsonObject1.getString("long");
                                String address = jsonObject1.getString("address");
                                vendor_avatar = jsonObject1.getString("vendor_avatar");
                                vendor_background_image = jsonObject1.getString("vendor_background_image");
                                String vendor_description = jsonObject1.getString("vendor_description");
                                String vendor_details = jsonObject1.getString("vendor_details");
                                String vendor_additionaldetails = jsonObject1.getString("vendor_additionaldetails");
                                vendor_city = jsonObject1.getString("vendor_city");
                                String vendor_cityA;
                                if (LanGet.equals("en")) {
                                    vendor_cityA = vendor_city + " In Products 2017";
                                    offersTxt2.setText(vendor_cityA);
                                    offersTxt1.setText("OFFERS CONTSINED IN PRODUCT");
                                } else if (LanGet.equals("ar")) {
                                    vendor_cityA = vendor_city + "في منتجات 2017";
                                    offersTxt2.setText(vendor_cityA);
                                    offersTxt1.setText("عروض كونتينسيند في المنتج");
                                }

                                PrefManager aa = new PrefManager(getActivity());
                                /*aa.setLat(lat);
                                aa.setLng(lng);*/
                                aa.setVendorCity(vendor_city);
                                //aa.setVendorAddress(address);
                                aa.setVendorDesc(vendor_description);
                                Picasso.with(getActivity()).load(vendor_background_image).resize(400, 200).into(ImageHeader);
                                ImageHeader.setBackgroundColor(Color.TRANSPARENT);
                                Picasso.with(getActivity()).load(vendor_avatar).into(HeaderIcon);
                                PrefManager prefManager=new PrefManager(getActivity());
                                address1.setText(prefManager.getvendorAddress());
                                Icon.setVisibility(View.VISIBLE);
                                Share.setVisibility(View.VISIBLE);

                                Share.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent i = new Intent(getActivity(), FoodShare.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("vendor_avatar", vendor_avatar);
                                        bundle.putString("vendor_background_image", vendor_background_image);
                                        i.putExtras(bundle);
                                        startActivity(i);
                                        getActivity().overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                    }
                                });

                                Icon.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        Log.i("Phone",phone);
                                    }
                                });
                                listView.setAdapter(null);

                                JSONArray jsonArray = new JSONArray(jsonObject1.getString("offers"));
                                adapter = new FoodOffersAdapter2(getActivity(), list);
                                listView.setAdapter(adapter);
                                list.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject actor = jsonArray.getJSONObject(i);
                                    Log.i("Array2",actor.toString());
                                    FoodOffers2 dataSet = new FoodOffers2();
                                    dataSet.setOffer_id(actor.getString("offer_id"));
                                    dataSet.setOffer_name(actor.getString("offer_name"));
                                    Offer_Name = actor.getString("end_date");
                                    dataSet.setStart_date(actor.getString("start_date"));
                                    dataSet.setEnd_date(actor.getString("end_date"));
                                    //dataSet.setOffer_status(actor.getString("offer_status"));
                                    dataSet.setOffer_original_price(actor.getString("offer_original_price"));
                                    dataSet.setOffer_discounted_price(actor.getString("offer_discounted_price"));
                                    dataSet.setOffer_saving_price(actor.getString("offer_saving_price"));
                                    dataSet.setOffer_detail(actor.getString("offer_details"));
                                    dataSet.setOffer_limit(actor.getString("offer_limit"));
                                    String ab = actor.getString("offer_original_price");
                                    Log.i("Offer", ab);
                                    list.add(dataSet);
                                    Log.i("List1",list.toString());
                                    //offer_name.setText("aa");
                                }
                                Log.i("Mohsin", jsonArray.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);
    }


    public void getPin(){
        String user_pin_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/validate/pin";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JSONObject params = new JSONObject();
            params.put("secret_pin",user_pin);

            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, user_pin_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String status=jsonObject.getString("status");
                        Log.i("UserResponse",jsonObject.toString());
                        if(status.equals("true"))
                        {
                            flag=2;
                            Log.i("aa",status);
                            //Toast.makeText(getActivity(),"Pin Match",Toast.LENGTH_SHORT).show();
                            mainRelative.setBackground( getResources().getDrawable(R.drawable.vendor_rec));
                            inner3.setBackground( getResources().getDrawable(R.drawable.vendor_rec));
                            redemption_txt.setText("Please ask Vendor to enter their PIN");
                            edtxt1.getText().clear();
                            edtxt2.getText().clear();
                            edtxt3.getText().clear();
                            edtxt4.getText().clear();
                            edtxt1.requestFocus();
                            user="2";
                        }
                        else if(status.equals("false"))
                        {
                            Log.i("Response","False");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        try {
                            JSONObject jsonObject=new JSONObject(jsonError);
                            Log.i("UserResponse2",jsonObject.toString());
                            if(flag==1) {
                                //Toast.makeText(getActivity(), "User Pin Not Match", Toast.LENGTH_SHORT).show();
                                edtxt1.getText().clear();
                                edtxt2.getText().clear();
                                edtxt3.getText().clear();
                                edtxt4.getText().clear();
                                // Animation & Vibration //
                                Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                                edtxt1.startAnimation(shake);
                                edtxt2.startAnimation(shake);
                                edtxt3.startAnimation(shake);
                                edtxt4.startAnimation(shake);
                                Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                vibe.vibrate(100);
                                edtxt1.requestFocus();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    /*String message = null;
                    if (error instanceof NetworkError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof ServerError) {
                        message = "The server could not be found. Please try again after some time!!";
                        Log.i("LOG_VOLLEY", message);
                        edtxt1.getText().clear();
                        edtxt2.getText().clear();
                        edtxt3.getText().clear();
                        edtxt4.getText().clear();
                        // Animation & Vibration //
                        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                        edtxt1.startAnimation(shake);
                        edtxt2.startAnimation(shake);
                        edtxt3.startAnimation(shake);
                        edtxt4.startAnimation(shake);
                        Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                        vibe.vibrate(100);
                        edtxt1.requestFocus();
                    } else if (error instanceof AuthFailureError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof ParseError) {
                        message = "Parsing error! Please try again after some time!!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof NoConnectionError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof TimeoutError) {
                        message = "Connection TimeOut! Please check your internet connection.";
                        Log.i("LOG_VOLLEY", message);
                    }*/

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

    public void getPin_Vendor(){
        String vendor_pin_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/vendor/validate/pin";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JSONObject params = new JSONObject();
            params.put("vendor_id",vendor_id);
            params.put("secret_pin",user_pin);

            final String mRequestBody = params.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, vendor_pin_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        Log.i("Response",jsonObject.toString());
                        String status=jsonObject.getString("status");
                        if(status.equals("true"))
                        {
                            Log.i("Response123","True");
                            user="1";
                            Log.i("aa",status);
                            offer_check();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        try {
                            JSONObject jsonObject=new JSONObject(jsonError);
                            Log.i("Response123",jsonObject.toString());
                            edtxt1.getText().clear();
                            edtxt2.getText().clear();
                            edtxt3.getText().clear();
                            edtxt4.getText().clear();
                            // Animation & Vibration //
                            Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                            edtxt1.startAnimation(shake);
                            edtxt2.startAnimation(shake);
                            edtxt3.startAnimation(shake);
                            edtxt4.startAnimation(shake);
                            Vibrator vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                            vibe.vibrate(100);
                            edtxt1.requestFocus();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    /*String message = null;
                    if (error instanceof NetworkError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof ServerError) {
                        message = "The server could not be found. Please try again after some time!!";
                        Log.i("LOG_VOLLEY", message);
                        Toast.makeText(getActivity(),"Vendor Pin Not Match",Toast.LENGTH_SHORT).show();
                        edtxt1.getText().clear();
                        edtxt2.getText().clear();
                        edtxt3.getText().clear();
                        edtxt4.getText().clear();
                        edtxt1.requestFocus();
                    } else if (error instanceof AuthFailureError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof ParseError) {
                        message = "Parsing error! Please try again after some time!!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof NoConnectionError) {
                        message = "Cannot connect to Internet...Please check your connection!";
                        Log.i("LOG_VOLLEY", message);
                    } else if (error instanceof TimeoutError) {
                        message = "Connection TimeOut! Please check your internet connection.";
                        Log.i("LOG_VOLLEY", message);
                    }*/
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



    public void getRule()
    {
        String profile_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/rules?lang=en";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, profile_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            rule_list.clear();
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.d("Response",response1);
                            JSONArray jsonArray=new JSONArray(response1);
                            for(int i=0;i<jsonArray.length();i++)
                            {
                                rule_adapter = new RuleAdapter(getActivity(), rule_list);
                                RuleListView.setAdapter(rule_adapter);
                                RuleModel ruleModel=new RuleModel();
                                JSONObject actor = jsonArray.getJSONObject(i);
                                String rule_id = actor.getString("id");
                                String description=actor.getString("description");
                                ruleModel.setRule_id(rule_id);
                                ruleModel.setDescription(description);
                                rule_list.add(ruleModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
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

        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);
    }

    public void get_check_pin()
    {
        String check_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/secretPin";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, check_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            key_check=jsonObject.getString("status");
                            Log.i("Key",key_check);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);
    }

    /*public void get_Address()
    {
        branchModel.setAddress("ABC");
        branch_list.add(branchModel);


    }*/

    public void offer_check()
    {
        String offer_urlA=Base_Url+"/bitmoonbackend/public/index.php/api/user/offer/redeem/"+offer_id+"?lang="+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, offer_urlA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.i("OfferID",response1);
                            dialog.dismiss();
                            Toast.makeText(getActivity(),"Thank You To Avail This Offer",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);
                    try {
                        JSONObject jsonObject=new JSONObject(jsonError);
                        JSONObject jsonObject1=new JSONObject(jsonObject.getString("error"));
                        Log.d("OfferID", jsonObject1.toString());
                        dialog.dismiss();
                        Toast.makeText(getActivity(),"You cannot avail this offer more than its limit",Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);
    }


    public void get_Offer_Detail()
    {
        String url=Base_Url+"/bitmoonbackend/public/index.php/api/user/offer/"+vendor_id+"/"+offer_id+"?lang="+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response2217",response);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject jsonObject1=new JSONObject(jsonObject.getString("response"));
                            Log.d("Response2217",jsonObject1.toString());
                            offer_name.setText(jsonObject1.getString("offer_name"));
                            offer_name2.setText(jsonObject1.getString("offer_name"));
                            offer_desc.setText(jsonObject1.getString("offer_details"));
                            estimated_savings.setText(jsonObject1.getString("offer_discounted_price"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);
    }


    public void get_offer_limit()
    {
        String offer_limit_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/limit/"+offer_id+"?lang="+LanGet;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, offer_limit_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.i("LimitOffer",response1);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error is ", "" + error);
                //Toast.makeText(getActivity(),"You cannot Avail This Offer",Toast.LENGTH_SHORT).show();
                displayAlert("NotAvail");
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

        MySingleton.getmInstance(getActivity()).addToRequestque(stringRequest);
    }

    public void displayAlert(String str) {
        if (str.equals("CreatePin")) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            break;

                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Bitmoon");
            builder.setMessage("Please Create Your PIN First").setPositiveButton("OK", dialogClickListener).show();
        }
        else if(str.equals("NotAvail"))
        {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Bitmoon");
            builder.setMessage("You cannot Avail this offer more than its Limit").setPositiveButton("OK", dialogClickListener).show();
        }
    }

}
