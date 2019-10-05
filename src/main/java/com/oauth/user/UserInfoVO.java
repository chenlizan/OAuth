package com.oauth.user;

import com.oauth.mongo.entity.UserInfo;


public class UserInfoVO {

    private String id;
    private String username;
    private String nickname;

    public UserInfoVO() {
    }

    public UserInfoVO(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.username = userInfo.getUsername();
        this.nickname = userInfo.getNickname();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
