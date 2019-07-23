package com.oauth.user;

import org.springframework.stereotype.Component;

@Component
public class UserInfo {

    private String user;
    private String password;

    public UserInfo() {

    }

    public UserInfo(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
