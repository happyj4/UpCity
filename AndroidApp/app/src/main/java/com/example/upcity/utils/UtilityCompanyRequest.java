package com.example.upcity.utils;

public class UtilityCompanyRequest {
    private int ut_company_id;
    private String name;
    private String address;
    private String phone;
    private int rating;

    public UtilityCompanyRequest(int ut_company_id, String name, String address, String phone, int rating) {
        this.ut_company_id = ut_company_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }
}
