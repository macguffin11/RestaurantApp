package com.example.restaurantapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Geocode {
    @SerializedName("location")
    @Expose
    private LocationDetail locality;
    @SerializedName("link")
    @Expose
    private String link;

    public LocationDetail getLocation() {
        return locality;
    }

    public void setLocation(LocationDetail locality) {
        this.locality = locality;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
