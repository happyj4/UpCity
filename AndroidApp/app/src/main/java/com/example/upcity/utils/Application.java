package com.example.upcity.utils;

public class Application {
    private int id;
    private String name;
    private String description;
    private String address;
    private double longitude;
    private double latitude;
    private String status;
    private String creationDate;
    private int userId;
    private int kpId;
    private int imageId;
    private int bitId;

    public Application(int id, String name, String description, String address, int kpid, String creationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.kpId = kpid;
        this.creationDate = creationDate;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getKpId() {
        return kpId;
    }

    public void setKpId(int kpId) {
        this.kpId = kpId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getBitId() {
        return bitId;
    }

    public void setBitId(int bitId) {
        this.bitId = bitId;
    }
}
