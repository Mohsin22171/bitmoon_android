package com.example.mohsin.bitmoonv3.models;


public class PurchaseModel {

    public String PurchaseHeading;
    public String PurchaseDesciption;
    public int PurchaseHeader;

    public PurchaseModel(String Heading,String Description,int Header)
    {
        this.PurchaseHeading=Heading;
        this.PurchaseDesciption=Description;
        this.PurchaseHeader=Header;
    }

    public String getPurchaseHeading()
    {
        return PurchaseHeading;
    }

    public void setPurchaseHeading(String PurchaseHeading)
    {
        this.PurchaseHeading=PurchaseHeading;
    }

    public String getPurchaseDesciption()
    {
        return PurchaseDesciption;
    }

    public void setPurchaseDesciption(String PurchaseDescription)
    {
        this.PurchaseDesciption=PurchaseDescription;
    }

    public int getPurchaseHeader()
    {
        return PurchaseHeader;
    }

    public void setPurchaseHeader(int PurchaseHeader)
    {
        this.PurchaseHeader=PurchaseHeader;
    }
}
