package com.example.upcity.utils;

public class ResponseGooglePay {
    public String status;
    public String message;
    public int subscription_id = -1;

    public ResponseGooglePay(String status, String message, int subscription_id) {
        this.status = status;
        this.message = message;
        this.subscription_id = subscription_id;
    }

    public ResponseGooglePay(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
