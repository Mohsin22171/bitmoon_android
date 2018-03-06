package com.example.mohsin.bitmoonv3.models;


public class BranchModel {
    String Branch_id;
    String Branch_Name;
    String Lat;
    String Lng;

   /* public BranchModel(String BranchName,String Lat)
    {
        this.Branch_Name=BranchName;
        this.Lat=Lat;
    }*/

    public void setBranch_id(String Branch_id)
    {
        this.Branch_id=Branch_id;
    }

    public void setBranch_Name(String Branch_Name)
    {
        this.Branch_Name=Branch_Name;
    }

    public void setLat(String Lat)
    {
        this.Lat=Lat;
    }

    public void setLng(String Lng)
    {
        this.Lng=Lng;
    }

    public String getBranch_id()
    {
        return Branch_id;
    }

    public String getBranch_Name()
    {
        return Branch_Name;
    }

    public String getLat()
    {
        return Lat;
    }

    public String getLng()
    {
        return Lng;
    }

}

