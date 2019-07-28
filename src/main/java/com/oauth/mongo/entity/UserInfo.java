package com.oauth.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class UserInfo {
    @Id
    private String id;

    @Indexed(unique = true)
    private String user;

    private String password;

    private String phone;

    public UserInfo() {
    }

    public UserInfo(String user, String password, String phone) {
        this.user = user;
        this.password = password;
        this.phone = phone;
    }
}
