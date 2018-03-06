package com.example.mohsin.bitmoonv3.CustomerProfile;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mohsin.bitmoonv3.BottomTabs;
import com.example.mohsin.bitmoonv3.FoodDrink.LevelAdapter;
import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.SharedPreferences.PrefManager;
import com.example.mohsin.bitmoonv3.models.LevelModel;

import java.util.ArrayList;
import java.util.List;

import static android.util.LayoutDirection.LTR;
import static android.util.LayoutDirection.RTL;

public class Levels extends Activity{
    private List<LevelModel>list=new ArrayList<LevelModel>();
    private ListView Level_ListView;
    private LevelAdapter adapter;
    String Level_Name[];
    String Level_Desc[];
    int Level_Icon[];
    String level;
    ImageView back;
    RelativeLayout relativeLayout;
    String LanGet;
    TextView LevelsTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView2();
        preferences();
        if(LanGet.equals("en"))
        {
            relativeLayout.setLayoutDirection(LTR);
            LevelsTxt.setText("Levels");
            Level_Name=new String[]
                    {
                            "Warming up","Having Fun","Now we are talking","Big Save",
                            "Ultimate Save","Hero","All time Champ"
                    };
            Level_Desc=new String[]
                    {
                            "Claim at least 1 offer","Claim at least 5 offers","Claim at least 25 offers",
                            "Claim at least 50 offers","Claim at least 100 offers","Claim at least 150 offers",
                            "Claim at least 200 offers"
                    };
        }
        else if(LanGet.equals("ar"))
        {
            relativeLayout.setLayoutDirection(RTL);
            LevelsTxt.setText("مستويات");
            Level_Name=new String[]
                    {
                            "الاحماء","استمتع","الآن نحن نتحدث","حفظ كبير",
                            "حفظ في نهاية المطاف","بطل","كل بطل الوقت"
                    };
            Level_Desc=new String[]
                    {
                            "المطالبة 1 عروض على الأقل","المطالبة 5 عروض على الأقل","المطالبة 25 عروض على الأقل",
                            "المطالبة 50 عروض على الأقل","المطالبة 100 عروض على الأقل","المطالبة 150 عروض على الأقل",
                            "المطالبة 200 عروض على الأقل"
                    };
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(), BottomTabs.class);
                Bundle bundle=new Bundle();
                bundle.putString("name","Profile");
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        Level_Icon=new int[]
                {
                        R.drawable.la_g,R.drawable.lb_g,R.drawable.lc_g,
                        R.drawable.ld_g,R.drawable.le_g,R.drawable.lf_g,
                        R.drawable.lg_g
                };

        for(int i=0;i<Level_Name.length;i++)
        {
            LevelModel levelModel=new LevelModel(Level_Name[i],Level_Desc[i],Level_Icon[i]);
            list.add(levelModel);
        }
        adapter=new LevelAdapter(this,list);
        Level_ListView.setAdapter(adapter);

        if(level.equals("1"))
        {
            list.get(0).setLevl_Icon(R.drawable.la_c);
        }
        else if(level.equals("2"))
        {
            list.get(0).setLevl_Icon(R.drawable.la_c);
            list.get(1).setLevl_Icon(R.drawable.lb_c);
        }
        else if(level.equals("3"))
        {
            list.get(0).setLevl_Icon(R.drawable.la_c);
            list.get(1).setLevl_Icon(R.drawable.lb_c);
            list.get(2).setLevl_Icon(R.drawable.lc_c);
        }
        else if(level.equals("4"))
        {
            list.get(0).setLevl_Icon(R.drawable.la_c);
            list.get(1).setLevl_Icon(R.drawable.lb_c);
            list.get(2).setLevl_Icon(R.drawable.lc_c);
            list.get(3).setLevl_Icon(R.drawable.ld_c);
        }
        else if(level.equals("5"))
        {
            list.get(0).setLevl_Icon(R.drawable.la_c);
            list.get(1).setLevl_Icon(R.drawable.lb_c);
            list.get(2).setLevl_Icon(R.drawable.lc_c);
            list.get(3).setLevl_Icon(R.drawable.ld_c);
            list.get(4).setLevl_Icon(R.drawable.le_c);
        }
        else if(level.equals("6"))
        {
            list.get(0).setLevl_Icon(R.drawable.la_c);
            list.get(1).setLevl_Icon(R.drawable.lb_c);
            list.get(2).setLevl_Icon(R.drawable.lc_c);
            list.get(3).setLevl_Icon(R.drawable.ld_c);
            list.get(4).setLevl_Icon(R.drawable.le_c);
            list.get(4).setLevl_Icon(R.drawable.lf_c);
        }
        else if(level.equals("7"))
        {
            list.get(0).setLevl_Icon(R.drawable.la_c);
            list.get(1).setLevl_Icon(R.drawable.lb_c);
            list.get(2).setLevl_Icon(R.drawable.lc_c);
            list.get(3).setLevl_Icon(R.drawable.ld_c);
            list.get(4).setLevl_Icon(R.drawable.le_c);
            list.get(4).setLevl_Icon(R.drawable.lf_c);
            list.get(4).setLevl_Icon(R.drawable.lg_c);
        }
    }

    public void initView2()
    {
        back=(ImageView)findViewById(R.id.back);
        Level_ListView=(ListView)findViewById(R.id.level_list);
        Bundle bundle=getIntent().getExtras();
        level=bundle.getString("Level");
        Log.d("Level",level);
        relativeLayout=(RelativeLayout)findViewById(R.id.level_main);
        LevelsTxt=(TextView)findViewById(R.id.FoodText);
    }

    public void preferences()
    {
        PrefManager prefManager=new PrefManager(getApplicationContext());
        LanGet=prefManager.getLanguage();
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(), BottomTabs.class);
        Bundle bundle=new Bundle();
        bundle.putString("name","Profile");
        i.putExtras(bundle);
        startActivity(i);
    }
}
