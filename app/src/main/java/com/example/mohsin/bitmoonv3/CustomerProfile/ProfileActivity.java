package com.example.mohsin.bitmoonv3.CustomerProfile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.Login.MySingleton;
import com.example.mohsin.bitmoonv3.Login.NewTest;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ProfileActivity extends Activity{
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageView camera,Settings;
    private ImageView profile_image;
    private String userChoosenTask,token;
    TextView Savings,ProfileName,ProfileEmail,Levels;
    RelativeLayout ProfilePage;
    Button LevelBtn,BreakDown_Btn;
    Bundle bundle;
    String Base_Url,Lang;
    TextView HeaderTxt,TotalSavings,LevelTxt,WarmingTxt,Product1,Product2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView2();
        preferences();
        Base_Url=getString(R.string.Base_Url);
        ProfilePage.setVisibility(View.GONE);
        getProfile();

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),CustomerProfileActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_left);
            }
        });

        /*camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });*/

        LevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), com.example.mohsin.bitmoonv3.CustomerProfile.Levels.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        if(Lang.equals("en"))
        {
            HeaderTxt.setText("My Profile");
            TotalSavings.setText("Total Savings This Year");
            BreakDown_Btn.setText("SAVINGS BREAKDOWN");
            LevelBtn.setText("VIEW ALL LEVELS");
            LevelTxt.setText("Current Level");
            WarmingTxt.setText("Warming Up");
            Product1.setText("My Products");
            Product2.setText("Buy a Product to see something here");
        }
        else if(Lang.equals("ar"))
        {
            HeaderTxt.setText("ملفي");
            TotalSavings.setText("إجمالي الادخار هذا العام");
            BreakDown_Btn.setText("التوفير انهيار");
            LevelBtn.setText("عرض جميع المستويات");
            LevelTxt.setText("المستوى الحالي");
            WarmingTxt.setText("الاحترار");
            Product1.setText("منتجاتي");
            Product2.setText("شراء منتج لرؤية شيء هنا");
        }
    }

    public void onBackPressed()
    {
        Intent i=new Intent(getApplicationContext(), BottomTabs.class);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Capture"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Gallery"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Gallery", "Capture",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Update Profile Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(ProfileActivity.this);

                if (items[item].equals("Capture")) {
                    userChoosenTask ="Capture";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Gallery")) {
                    userChoosenTask ="Gallery";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profile_image.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profile_image.setImageBitmap(bm);
    }

    public void getProfile()
    {
        String profile_url=Base_Url+"/bitmoonbackend/public/index.php/api/user/savings";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, profile_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String response1=jsonObject.getString("response");
                            Log.i("Response",response1);
                            JSONObject jsonObject1=new JSONObject(response1);
                            Log.i("Savings",jsonObject1.toString());
                            String savings = jsonObject1.getString("total_savings");
                            String level=jsonObject1.getString("level");
                            String fname=jsonObject1.getString("first_name");
                            String lname=jsonObject1.getString("last_name");
                            String email=jsonObject1.getString("email");
                            Savings.setText("USD "+savings);
                            Levels.setText(level);
                            ProfileEmail.setText(email);
                            ProfileName.setText(fname+" "+lname);
                            ProfilePage.setVisibility(View.VISIBLE);
                            bundle=new Bundle();
                            bundle.putString("Level",level);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProfileActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
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

        MySingleton.getmInstance(ProfileActivity.this).addToRequestque(stringRequest);
    }


    public void initView2()
    {
        Settings=(ImageView)findViewById(R.id.settings);
        //camera=(ImageView)findViewById(R.id.camera);
        profile_image=(ImageView)findViewById(R.id.profile_image);
        Savings=(TextView)findViewById(R.id.savings);
        ProfileName=(TextView)findViewById(R.id.profile_name);
        ProfileEmail=(TextView)findViewById(R.id.profile_email);
        Levels=(TextView)findViewById(R.id.levels);
        ProfilePage=(RelativeLayout)findViewById(R.id.body);
        LevelBtn=(Button)findViewById(R.id.level_btn);
        HeaderTxt=(TextView)findViewById(R.id.header);
        TotalSavings=(TextView)findViewById(R.id.savings_txt);
        BreakDown_Btn=(Button)findViewById(R.id.breakdown_btn);
        LevelBtn=(Button)findViewById(R.id.level_btn);
        LevelTxt=(TextView)findViewById(R.id.level_txt);
        WarmingTxt=(TextView)findViewById(R.id.rookie_txt);
        Product1=(TextView)findViewById(R.id.product_txt);
        Product2=(TextView)findViewById(R.id.product_txt2);
    }

    public void preferences()
    {
        PrefManager prefManager=new PrefManager(getApplicationContext());
        token=prefManager.getToken();
        Lang=prefManager.getLanguage();
    }




}
