package com.example.upcity;

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

    public Application(int id, String name, String creationDate, int kpid) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.kpId = kpid;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getKpid() {
        return kpId;
    }

    public void setKpid(int kpid) {
        this.kpId = kpid;
    }

    public void imageId(int imageId) {
        this.imageId = imageId;
    }

    public void bitId(int bitId) {
        this.bitId = bitId;
    }

}
