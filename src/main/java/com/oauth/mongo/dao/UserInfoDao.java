package com.oauth.mongo.dao;

import com.oauth.mongo.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInfoDao extends MongoRepository<UserInfo, String> {

    public UserInfo findByUsername(String username);
}
