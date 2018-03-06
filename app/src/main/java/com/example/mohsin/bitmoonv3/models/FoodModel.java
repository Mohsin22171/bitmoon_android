package com.example.mohsin.bitmoonv3.models;

public class FoodModel  {
    private String name;
    private String address;
    private String distance;
    private int icon;

    public FoodModel(String name,String address,String kilometer,int icon)
    {
        this.name=name;
        this.address=address;
        this.distance=kilometer;
        this.icon=icon;
    }

    public String getName()
    {
        return this.name;
    }

    public String getAddress()
    {
        return this.address;
    }

    public String getDistance()
    {
        return this.distance;
    }

    public int getIcon()
    {
        return this.icon;
    }
}