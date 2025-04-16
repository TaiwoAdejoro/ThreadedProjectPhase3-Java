package org.example.travelexpertsphase3desktop.model;

public class Login {
    private int userId;
    private String username;
    private boolean  isManager;

    public Login(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.isManager = role.equalsIgnoreCase("Agent Manager");
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return username;
    }

    public boolean isManager() {
        return isManager;
    }
}
