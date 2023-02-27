package com.bmc206p14app.models;

public class ContactItems {
    // data members
    private int ImageID;
    private String Name;
    private String Phone;
    private String Email;

    // constructor
    public ContactItems(int img, String name, String phone, String email){
        this.ImageID = img;
        this.Name = name;
        this.Phone = phone;
        this.Email = email;
    }
    // getter methods
    public int getImageID() {
        return ImageID;
    }
    public String getName() {
        return Name;
    }
    public String getPhone() {
        return Phone;
    }
    public String getEmail() {
        return Email;
    }
}
