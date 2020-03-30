package com.example.project1.model;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Hotel {
    private String id_Hotel;
    private String name_Hotel;
    private String address_Hotel;
    private String description_Hotel;
    private String image_Hotel;
    private String id_User;
    private String pubDate_Hotel;
    private String name_Place;

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

    public String getId_User() {
        return id_User;
    }

    public void setId_User(String id_User) {
        this.id_User = id_User;
    }

    public String getPubDate_Hotel() {
        return pubDate_Hotel;
    }

    public void setPubDate_Hotel(String pubDate_Hotel) {
        this.pubDate_Hotel = pubDate_Hotel;
    }
}
