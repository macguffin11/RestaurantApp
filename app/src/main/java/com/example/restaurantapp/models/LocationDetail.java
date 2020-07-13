package com.example.restaurantapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationDetail {
    @SerializedName("entity_type")
    @Expose
    private String entityType ;
    @SerializedName("entity_id")
    @Expose
    private int entityId ;
    @SerializedName("latitude")
    @Expose
    private double latitude ;
    @SerializedName("longitude")
    @Expose
    private double longitude ;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
