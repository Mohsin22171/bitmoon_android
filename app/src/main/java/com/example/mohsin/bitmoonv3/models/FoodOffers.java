package com.example.mohsin.bitmoonv3.models;

public class FoodOffers {
    String offers;
    String date;
    int food_icon;
    String address;
    String offers_txt;

    public FoodOffers(String offers,String date,int food_icon)
    {
        this.offers=offers;
        this.date=date;
        this.food_icon=food_icon;
    }

    public String getOffers()
    {
        return offers;
    }

    public void setOffers(String offers)
    {
        this.offers=offers;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate()
    {
        this.date=date;
    }

    public int getFood_icon()
    {
        return food_icon;
    }

    public void setFood_icon()
    {
        this.food_icon=food_icon;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address=address;
    }

    public String getOffers_txt()
    {
        return offers_txt;
    }

    public void setOffers_txt(String offers_txt)
    {
        this.offers_txt=offers_txt;
    }
}
