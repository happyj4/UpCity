package com.example.upcity.utils;

public class ResponseGoogleAuthentication {
    public String message;
    public String access_token;
    public String token_type;
    public User user;

    public static class User {
        public String name;
        public String surname;
        public String image;
    }
}
