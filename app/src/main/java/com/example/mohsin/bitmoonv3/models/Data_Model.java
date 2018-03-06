package com.example.mohsin.bitmoonv3.models;


public class Data_Model {
    private String name,image,logo,more,category,vendor_id;
    private int logo2;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public void setLogo(String logo)
    {
        this.logo=logo;
    }

    public String getLogo()
    {
        return  logo;
    }



    public void setMore(String more)
    {
        this.more=more;
    }

    public String getMore()
    {
        return more;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category=category;
    }

    public void setLogo2(int logo2)
    {
        this.logo2=logo2;
    }

    public int getLogo2()
    {
        return  logo2;
    }

    public void setVendor_id_h(String vendor_id)
    {
        this.vendor_id=vendor_id;
    }

    public String getVendor_id_h()
    {
        return  vendor_id;
    }




}