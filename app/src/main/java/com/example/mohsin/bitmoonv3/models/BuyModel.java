package com.example.mohsin.bitmoonv3.models;


public class BuyModel {

    public String Title;
    public String Price;

    public BuyModel(String Title,String Price)
    {
        this.Title=Title;
        this.Price=Price;
    }

    public String getTitle()
    {
        return Title;
    }

    public void setTitle(String Title)
    {
        this.Title=Title;
    }

    public String getPrice()
    {
        return Price;
    }

    public void  setPrice(String Price)
    {
        this.Price=Price;
    }
}
