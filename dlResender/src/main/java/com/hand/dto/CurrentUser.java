package com.hand.dto;

import java.util.Map;

/**
 * Created by DongFan on 2016/12/15.
 */
public class CurrentUser {
    private String userId;
    private String email;
    private String mobileNumber;
    private String name;
    private String username;
    private String groups;

    public CurrentUser(Map<String, String> user) {
        this.userId = user.get("userId") != null ? user.get("userId") : "";
        this.email = user.get("email") != null ? user.get("email") : "";
        this.mobileNumber = user.get("mobileNumber") != null ? user.get("mobileNumber") : "";
        this.name = user.get("name") != null ? user.get("name") : "";
        this.username = user.get("username") != null ? user.get("username") : "";
        this.groups = user.get("groups") != null ? user.get("group") : "";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }
}
