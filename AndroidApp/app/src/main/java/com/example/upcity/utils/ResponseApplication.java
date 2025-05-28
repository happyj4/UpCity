package com.example.upcity.utils;

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
    private UtilityCompany utility_company;
    private Image image;
    private Report report;
    private User user;

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

    public UtilityCompany getUtility_company() {
        return utility_company;
    }

    public Image getImage() {
        return image;
    }

    public Report getReport() {
        return report;
    }

    public User getUser() {
        return user;
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
        private Image image;

        public int getReport_id() {
            return report_id;
        }

        public Date getExecution_date() {
            return execution_date;
        }

        public Image getImage() {
            return image;
        }
    }

    public static class User {
        private int user_id;
        private String name;
        private String surname;

        public int getUser_id() {
            return user_id;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }
    }
}
