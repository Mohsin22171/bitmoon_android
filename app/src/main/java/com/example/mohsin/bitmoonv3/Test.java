package com.example.mohsin.bitmoonv3;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mohsin.bitmoonv3.FoodDrink.FoodDetail2;
import com.example.mohsin.bitmoonv3.Tabs.ShareFriends;
import com.example.mohsin.bitmoonv3.models.FoodModel;

import java.util.ArrayList;
import java.util.List;


public class Test extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        final Dialog dialog = new Dialog(Test.this);

        dialog.setContentView(R.layout.redeem_code_dialog1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




        dialog.show();

    }
}

