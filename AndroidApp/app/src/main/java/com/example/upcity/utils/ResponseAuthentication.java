package com.example.upcity.utils;

public class ResponseAuthentication {
    private String message;
    private String access_token;
    private String tokenType;
    private User user;

    public String getMessage() { return message; }
    public String getAccessToken() { return access_token; }
    public String getTokenType() { return tokenType; }
    public User getUser() { return user; }

    public static class User {
        private String name;
        private String surname;
        private String image;

        public String getName() { return name; }
        public String getSurname() { return surname; }
        public String getImage() { return image; }
    }
}