package com.example.mohsin.bitmoonv3.models;


public class AddressModel {

    public String Address;
    public String Street;
    public String Distance;

    public AddressModel(String Address,String Street,String Distance)
    {
        this.Address=Address;
        this.Street=Street;
        this.Distance=Distance;
    }

    public String getAddress()
    {
        return Address;
    }

    public void setAddress(String Address)
    {
        this.Address=Address;
    }

    public String getStreet()
    {
        return Street;
    }

    public void setStreet(String Street)
    {
        this.Street=Street;
    }

    public String getDistance()
    {
        return Distance;
    }

    public void setDistance(String Distance)
    {
        this.Distance=Distance;
    }

}
