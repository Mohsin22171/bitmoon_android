package com.example.mohsin.bitmoonv3.models;

public class CurrencyModel {
    private String firstName = "";
    private String lastName = "";
    private int id = 0;
    private String profilePic;

    /*public CurrencyModel(String firstName, int id, int pic) {
        this.firstName = firstName;
        this.id = id;
        this.profilePic = pic;

    }*/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}