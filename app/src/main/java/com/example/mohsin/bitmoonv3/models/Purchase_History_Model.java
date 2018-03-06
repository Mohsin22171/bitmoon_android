package com.example.mohsin.bitmoonv3.models;


public class Purchase_History_Model {

    String offer_id;
    String offer_name;
    String offer_detail;


    public void setOffer_id(String offer_id)
    {
        this.offer_id=offer_id;
    }

    public void setOffer_name(String offer_name)
    {
        this.offer_name=offer_name;
    }

    public void setOffer_detail(String offer_detail)
    {
        this.offer_detail=offer_detail;
    }

    public String getOffer_id()
    {
        return offer_id;
    }

    public String getOffer_name()
    {
        return offer_name;
    }

    public String getOffer_detail()
    {
        return offer_detail;
    }
}
