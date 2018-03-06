package com.example.mohsin.bitmoonv3.models;


public class Fav_Model {
    String vendor_id;
    String vendor_name;
    String lat;
    String lng;
    String avatar;
    String address;

    public void setVendorId(String vendor_id)
    {
        this.vendor_id=vendor_id;
    }

    public void setVendorName(String vendor_name)
    {
        this.vendor_name=vendor_name;
    }

    public void setLat(String lat)
    {
        this.lat=lat;
    }

    public void setLng(String lng)
    {
        this.lng=lng;
    }

    public void setAvatar(String avatar)
    {
        this.avatar=avatar;
    }

    public void setAddress(String address)
    {
        this.address=address;
    }

    public String getVendorId()
    {
        return vendor_id;
    }

    public String getVendorName()
    {
        return vendor_name;
    }

    public String getLat()
    {
        return lat;
    }

    public String getLng()
    {
        return lng;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public String getAddress()
    {
        return address;
    }

}
