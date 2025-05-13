package com.example.upcity.utils;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class ApplicationRequest {
    @SerializedName("application_id")
    private int applicationId;

    private String name;
    private String status;

    @SerializedName("application_date")
    private Date applicationDate;

    @SerializedName("application_number")
    private int applicationNumber;

    @SerializedName("utility_company")
    private UtilityCompany utilityCompany;

    public int getApplicationId() {
        return applicationId;
    }

    public int getId() {
        return applicationId;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public int getApplicationNumber() {
        return applicationNumber;
    }

    public UtilityCompany getUtilityCompany() {
        return utilityCompany;
    }

    public static class UtilityCompany {
        private String name;

        public String getName() {
            return name;
        }
    }
}
