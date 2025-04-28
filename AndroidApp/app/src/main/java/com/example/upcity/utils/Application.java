package com.example.upcity.utils;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Application {
    private String name;
    private String status;

    @SerializedName("application_date")
    private Date applicationDate;

    @SerializedName("application_number")
    private int applicationNumber;

    @SerializedName("utility_company")
    private UtilityCompany utilityCompany;

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
