package com.example.mohsin.bitmoonv3.models;


public class Merchant_Model {

    public int thumbmail;
    public int logoo;
    public String res_name;
    public String type;

    public Merchant_Model(String res_name, String type, int thumbmail, int logoo)
    {
        this.res_name=res_name;
        this.type=type;
        this.thumbmail=thumbmail;
        this.logoo=logoo;
    }


    public String getRes_name()
    {
        return res_name;
    }

    public void setRes_name(String res_name)
    {
        this.res_name=res_name;
    }

    public String get_Type()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type=type;
    }

    public int getThumbmail()
    {
        return thumbmail;
    }

    public void setThumbmail(int thumbmail)
    {
        this.thumbmail=thumbmail;
    }

    public int getLogoo()
    {
        return logoo;
    }

    public void setLogoo(int logoo)
    {
        this.logoo=logoo;
    }

}
