package com.example.mohsin.bitmoonv3.Notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.FoodDrink.FoodDetail2;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.VendorFavourites.FavAdapter;
import com.example.mohsin.bitmoonv3.VendorFavourites.FavouriteActivity;
import com.example.mohsin.bitmoonv3.models.Fav_Model;
import com.example.mohsin.bitmoonv3.models.Notification_Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mohsin.bitmoonv3.R.id.result;

/**
 * Created by Mohsin on 2/21/2018.
 */

public class Notification extends Activity {

    private ListView Notification_List;
    private List<Notification_Model>list=new ArrayList<Notification_Model>();
    private NotificationAdapter adapter;
    String token;
    String Base_Url;
    TextView Result;
    private ProgressBar progressBar;
    String LanGet;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notifications);
        init_view();
        preferences();
        Base_Url=getString(R.string.Base_Url);
        Result.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        get_notifications();
    }

    public void get_notifications()
    {
        String url=Base_Url+"/bitmoonbackend/public/index.php/api/user/get_notifications";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response",response.toString());
                        adapter= new NotificationAdapter(Notification.this,list);
                        Notification_List.setAdapter(adapter);
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            if(response1.equals("[]"))
                            {
                                Log.i("Response","Null");
                                Result.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                            else
                            {
                                Log.i("Response","Not Null");
                                JSONArray jsonArray=new JSONArray(response1);
                                //result.setVisibility(View.GONE);
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    Notification_Model notification_model=new Notification_Model();
                                    JSONObject actor = jsonArray.getJSONObject(i);
                                    notification_model.setOffer_id(actor.getString("offer_id"));
                                    notification_model.setVendor__id(actor.getString("vendor_id"));
                                    notification_model.set_Message(actor.getString("message"));
                                    list.add(notification_model);
                                }
                            }
                            Notification_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    String vendor_id = list.get(position).getVendor_id();
                                    PrefManager aa = new PrefManager(getApplicationContext());
                                    aa.setVendorId(vendor_id);
                                    Intent i = new Intent(getApplicationContext(), FoodDetail2.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Activity", "Notification");
                                    i.putExtras(bundle);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
                                }
                            });
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Notification.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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
        MySingleton.getmInstance(Notification.this).addToRequestque(stringRequest);
    }

    public void init_view()
    {
        Notification_List=(ListView)findViewById(R.id.notify_list);
        Result=(TextView)findViewById(result);
        progressBar=(ProgressBar)findViewById(R.id.progress);
    }

    public void preferences()
    {
        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();
        LanGet=prefManager.getLanguage();
    }
}
