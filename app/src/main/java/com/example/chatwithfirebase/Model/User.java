package com.example.chatwithfirebase.Model;

public class User {

    private String username, id, ImageURL;

    public User() {
    }

    public User(String username, String id, String ImageURL) {
        this.username = username;
        this.id = id;
        this.ImageURL = ImageURL;
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
