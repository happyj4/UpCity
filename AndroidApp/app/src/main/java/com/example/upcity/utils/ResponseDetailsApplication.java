package com.example.upcity.utils;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ResponseDetailsApplication {
    @SerializedName("application_id")
    private int applicationId;

    @SerializedName("application_number")
    private int applicationNumber;

    private String name;
    private String address;
    private String description;
    private String status;

    @SerializedName("application_date")
    private Date applicationDate;

    private double longitude;
    private double latitude;

    @SerializedName("utility_company")
    private UtilityCompany utilityCompany;

    @SerializedName("image")
    private Image image;

    public int getApplicationId() {
        return applicationId;
    }

    public int getApplicationNumber() {
        return applicationNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public UtilityCompany getUtilityCompany() {
        return utilityCompany;
    }

    public Image getImage() {
        return image;
    }

    public static class UtilityCompany {
        private String name;
        public String getName() {
            return name;
        }
    }

    public static class Image {
        @SerializedName("image_id")
        private int imageId;

        @SerializedName("image_url")
        private String imageUrl;

        public int getImageId() {
            return imageId;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }
}
