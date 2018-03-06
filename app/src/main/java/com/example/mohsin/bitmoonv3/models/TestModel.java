package com.example.mohsin.bitmoonv3.models;


import android.widget.TextView;

public class TestModel {

    String Text;
    int Image;

    public void setText(String Text)
    {
        this.Text=Text;
    }

    public void setImage(int Image)
    {
        this.Image=Image;
    }

    public String getText()
    {
        return Text;
    }

    public int getImage()
    {
        return Image;
    }
}
