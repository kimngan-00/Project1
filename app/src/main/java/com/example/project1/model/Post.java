package com.example.project1.model;

import java.io.Serializable;

public class Post implements Serializable {
    private String id, user, urlImage,urlAvatarUser , tittle, address, description, pubDate;
    private String place, category;
    private long longPubDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public long getLongPubDate() {
        return longPubDate;
    }

    public void setLongPubDate(long longPubDate) {
        this.longPubDate = longPubDate;
    }

    public String getUrlAvatarUser() {
        return urlAvatarUser;
    }

    public void setUrlAvatarUser(String urlAvatarUser) {
        this.urlAvatarUser = urlAvatarUser;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
