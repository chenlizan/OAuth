package com.oauth.mongo;

import com.oauth.mongo.entity.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInfoRepository extends MongoRepository<UserInfo, String> {
}
