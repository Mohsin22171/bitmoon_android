package com.example.mohsin.bitmoonv3.models;



public class LevelModel {

    String Level_Name;
    String Level_Desc;
    int Levl_Icon;

    public LevelModel(String Level_Name,String Level_Desc,int Level_Icon)
    {
        this.Level_Name=Level_Name;
        this.Level_Desc=Level_Desc;
        this.Levl_Icon=Level_Icon;
    }

    public void setLevel_Name(String LevelName)
    {
        this.Level_Name=LevelName;
    }

    public void setLevel_Desc(String LevelDesc)
    {
        this.Level_Desc=LevelDesc;
    }

    public void setLevl_Icon(int LevelIcon)
    {
        this.Levl_Icon=LevelIcon;
    }

    public String getLevel_Name()
    {
        return Level_Name;
    }

    public String getLevel_Desc()
    {
        return Level_Desc;
    }

    public int getLevl_Icon()
    {
        return Levl_Icon;
    }
}
