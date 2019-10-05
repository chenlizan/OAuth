package com.oauth.user;

import com.oauth.mongo.dao.UserInfoDao;
import com.oauth.mongo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService {

    @Autowired
    UserInfoDao userInfoDao;

    public UserInfo save(UserInfo userInfo) {
        return userInfoDao.save(userInfo);
    }
}
