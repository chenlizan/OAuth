package com.oauth.user;

import com.mongodb.MongoException;
import com.mongodb.client.result.UpdateResult;
import com.oauth.mongo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserInfoVO register(@RequestBody UserInfoDTO userInfoDTO) throws MongoException {
        UserInfo userInfoPO = userInfoService.save(new UserInfo(userInfoDTO.getUsername(), passwordEncoder.encode(userInfoDTO.getPassword()), userInfoDTO.getRoles()));
        return new UserInfoVO(userInfoPO);
    }

    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public String getCode(@RequestParam(required = false, value = "code") String code) {
        return code;
    }

    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public UserInfoVO updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) throws MongoException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        Update update = new Update();
        update.set("nickname", userInfoDTO.getNickname());
        UpdateResult updateResult = mongoTemplate.upsert(query, update, UserInfo.class);
        return null;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        System.out.println("test");
        return "success test";
    }

//    @GetMapping("/product/{id}")
//    public String getProduct(@PathVariable String id) {
//        //for debug
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return "product id : " + id;
//    }
//
//    @GetMapping("/order/{id}")
//    public String getOrder(@PathVariable String id) {
//        //for debug
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return "order id : " + id;
//    }

}
