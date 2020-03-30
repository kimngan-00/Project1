package com.example.project1.model;

public class User {
    private String Id;
    private String UserName;
    private String Email;
    private String Password;
    private String Type;
    private String uriAvatar;

    public User(String id, String userName, String email, String type, String uriAvatar) {
        this.Id = id;
        this.UserName = userName;
        this.Email = email;
        this.Type = type;
        this.uriAvatar = uriAvatar;
    }

    public User() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUriAvatar() {
        return uriAvatar;
    }

    public void setUriAvatar(String uriAvatar) {
        this.uriAvatar = uriAvatar;
    }
}
