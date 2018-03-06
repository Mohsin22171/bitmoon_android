package com.example.mohsin.bitmoonv3.models;

/**
 * Created by Mohsin on 2/21/2018.
 */

public class Notification_Model {

    String offer_id;
    String vendor_id;
    String message;
    String image;


    public void setOffer_id(String offer_id)
    {
        this.offer_id=offer_id;
    }

    public void setVendor__id(String vendor_id)
    {
        this.vendor_id=vendor_id;
    }

    public void set_Message(String message)
    {
        this.message=message;
    }

    public String getOffer_id()
    {
        return offer_id;
    }

    public String getVendor_id()
    {
        return vendor_id;
    }

    public String getMessage()
    {
        return message;
    }

    public String getImage()
    {
        return image;
    }
}
