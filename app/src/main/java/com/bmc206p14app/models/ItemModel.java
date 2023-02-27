package com.bmc206p14app.models;

public class ItemModel {
    // data members
    private int ImageID;
    private String ItemName;
    private double ItemPrice;
    private String ItemStatus;

    // constructor
    public ItemModel(int img, String name, double price, String status){
        this.ImageID = img;
        this.ItemName = name;
        this.ItemPrice = price;
        this.ItemStatus = status;
    }
    // getter methods
    public int getImageID() {
        return ImageID;
    }
    public String getItemName() {
        return ItemName;
    }
    public double getItemPrice() {
        return ItemPrice;
    }
    public String getItemStatus() {
        return ItemStatus;
    }
}
