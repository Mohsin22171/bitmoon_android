package com.example.mohsin.bitmoonv3.models;

public class Category_Model {
    private String name,image;
    private int image2;
    private String subCatId;

    public Category_Model(String name,int image2,String subCatId)
    {
        this.name=name;
        this.image2=image2;
        this.subCatId=subCatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImage() {
        return image;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSubCatId(String subCatId)
    {
        this.subCatId=subCatId;
    }

    public String getSubCatId()
    {
        return subCatId;
    }


}