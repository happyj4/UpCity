package com.example.upcity.utils;

public class ResponseUpdateProfile {
    private String message;
    private User user;

    public String getMessage() { return message; }
    public User getUser() { return user; }

    public static class User {
        private String name;
        private String surname;
        private String email;
        private String image;

        public String getName() { return name; }
        public String getSurname() { return surname; }
        public String getEmail() { return email; }
        public String getImage() { return image; }
    }
}
