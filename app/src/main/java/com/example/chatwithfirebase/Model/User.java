package com.example.chatwithfirebase.Model;

public class User {

    private String username;
    private String id;
    private String ImageURL;
    private String email;
    private String numberPhone;


    public User(String username, String id, String imageURL, String email, String numberPhone) {
        this.username = username;
        this.id = id;
        ImageURL = imageURL;
        this.email = email;
        this.numberPhone = numberPhone;
    }


    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageURL(String imageURL) {
        this.ImageURL = imageURL;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getImageURL() {
        return ImageURL;
    }
}
