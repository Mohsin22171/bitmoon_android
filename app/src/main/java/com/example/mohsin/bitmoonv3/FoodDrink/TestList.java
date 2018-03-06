package com.example.mohsin.bitmoonv3.FoodDrink;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import com.example.mohsin.bitmoonv3.R;
import com.example.mohsin.bitmoonv3.models.TestModel;

import java.util.ArrayList;
import java.util.List;

public class TestList extends Activity {
    private List<TestModel>list=new ArrayList<TestModel>();
    private ListView listView;
    private TestAdapter adapter;
    int position=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        listView=(ListView)findViewById(R.id.listView);
        adapter=new TestAdapter(this,list);
        listView.setAdapter(adapter);



        for(int i=0;i<2;i++) {
            TestModel testModel = new TestModel();
            testModel.setText("A");
            testModel.setImage(R.drawable.jordan);
            /*testModel.setText("B");
            testModel.setImage(R.drawable.lebanon);*/
            list.add(testModel);
        }

        String Text=list.get(position).getText();
        list.get(position).setImage(R.drawable.logo);
        Log.i("Text123",Text);
    }
}
