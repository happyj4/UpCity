package com.example.upcity.utils;

public class LoginRequest {
    private String grantType = "password";
    private String username;
    private String password;
    private String scope = "";
    private String clientId = "";
    private String clientSecret = "";

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getGrantType() { return grantType; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getScope() { return scope; }
    public String getClientId() { return clientId; }
    public String getClientSecret() { return clientSecret; }
}