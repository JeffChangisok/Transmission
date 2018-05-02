package com.example.jeffchang.transmission.dao;

public class MyMenuItem{
    private String itemText;
    private int imageId;
    public MyMenuItem(String itemText,int imageId){
        this.itemText = itemText;
        this.imageId = imageId;
    }
    public String getItemText() {
        return itemText;
    }
    public int getImageId() {
        return imageId;
    }
}
