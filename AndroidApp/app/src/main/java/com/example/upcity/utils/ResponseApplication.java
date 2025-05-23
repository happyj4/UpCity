package com.example.upcity.utils;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class ResponseApplication {
    private int application_id;
    private int application_number;
    private String name;
    private String address;
    private String description;
    private String status;
    private Date application_date;
    private int user_rating;
    private double longitude;
    private double latitude;
    private ResponseApplication.UtilityCompany utility_company;
    private ResponseApplication.Image image;
    private ResponseApplication.Report report;

    public int getApplication_id() {
        return application_id;
    }

    public int getApplication_number() {
        return application_number;
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

    public Date getApplication_date() {
        return application_date;
    }

    public int getUser_rating() {
        return user_rating;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public ResponseApplication.UtilityCompany getUtility_company() {
        return utility_company;
    }

    public ResponseApplication.Image getImage() {
        return image;
    }

    public ResponseApplication.Report getReport() {
        return report;
    }

    public static class UtilityCompany {
        private String name;

        public String getName() {
            return name;
        }
    }

    public static class Image {
        private int image_id;
        private String image_url;

        public int getImage_id() {
            return image_id;
        }

        public String getImage_url() {
            return image_url;
        }
    }

    public static class Report {
        private int report_id;
        private Date execution_date;
        private ResponseApplication.Image image;

        public int getReport_id() {
            return report_id;
        }

        public Date getExecution_date() {
            return execution_date;
        }

        public ResponseApplication.Image getImage() {
            return image;
        }
    }
}
