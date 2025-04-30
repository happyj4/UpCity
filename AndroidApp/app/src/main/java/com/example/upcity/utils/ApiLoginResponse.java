package com.example.upcity.utils;

public class ApiLoginResponse {
    private String message;
    private UserData data;

    public String getMessage() {
        return message;
    }

    public UserData getData() {
        return data;
    }

    public static class UserData {
        private String name;
        private String surname;

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }
    }
}
