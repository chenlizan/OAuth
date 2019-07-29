package com.oauth.user;

import com.mongodb.MongoException;
import com.oauth.mongo.repository.UserInfoRepository;
import com.oauth.mongo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseStatus()
    public Object register(@RequestBody UserInfo userInfo) throws MongoException {
        return userInfoRepository.save(userInfo);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo);
        return "success";
    }

}
