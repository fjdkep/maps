package com.example.kien101.models;

import com.google.firebase.firestore.GeoPoint;

public class Shop {

    String image;
    String name;
    String address;
    String phoneNumber;
    int vote;
    String styles;
    GeoPoint location;

    public Shop(String image , String name, String address, String phoneNumber, int vote, String styles, GeoPoint location) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.vote = vote;
        this.address = address;
        this.styles = styles;
        this.location = location;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
