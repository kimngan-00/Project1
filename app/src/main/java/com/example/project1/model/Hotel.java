package com.example.project1.model;

import java.io.Serializable;

public class Hotel implements Serializable {
    private String id_Hotel;
    private String name_Hotel;
    private String address_Hotel;
    private String description_Hotel;
    private String image_Hotel;
    private String user_Name;
    private String pubDate_Hotel;
    private String name_Place;
    private String image_User;
    private String id_User;

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getImage_User() {
        return image_User;
    }

    public void setImage_User(String image_User) {
        this.image_User = image_User;
    }

    public String getName_Place() {
        return name_Place;
    }

    public void setName_Place(String name_Place) {
        this.name_Place = name_Place;
    }

    public Hotel() {
    }

    public String getId_Hotel() {
        return id_Hotel;
    }

    public void setId_Hotel(String id_Hotel) {
        this.id_Hotel = id_Hotel;
    }

    public String getName_Hotel() {
        return name_Hotel;
    }

    public void setName_Hotel(String name_Hotel) {
        this.name_Hotel = name_Hotel;
    }

    public String getAddress_Hotel() {
        return address_Hotel;
    }

    public void setAddress_Hotel(String address_Hotel) {
        this.address_Hotel = address_Hotel;
    }

    public String getDescription_Hotel() {
        return description_Hotel;
    }

    public void setDescription_Hotel(String description_Hotel) {
        this.description_Hotel = description_Hotel;
    }

    public String getImage_Hotel() {
        return image_Hotel;
    }

    public void setImage_Hotel(String image_Hotel) {
        this.image_Hotel = image_Hotel;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getPubDate_Hotel() {
        return pubDate_Hotel;
    }

    public void setPubDate_Hotel(String pubDate_Hotel) {
        this.pubDate_Hotel = pubDate_Hotel;
    }
}
