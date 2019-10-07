package com.oauth.mongo.dao;

import com.oauth.mongo.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserInfoDao extends MongoRepository<UserInfo, String> {

    public UserInfo findByUsername(String username);

    public Long deleteUserInfoByUsername(String username);


//    @Query
//    public void updateUserInfoByUsername(String username);

}
