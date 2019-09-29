package com.oauth.user;

import com.mongodb.MongoException;
import com.oauth.mongo.entity.UserInfo;
import com.oauth.mongo.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RestController
public class UserInfoController {

    private static class ResponseUserInfo implements Serializable {
        private String username;
        private String password;

        public ResponseUserInfo() {
        }

        public ResponseUserInfo(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Hello Spring Security";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody UserInfo userInfo) throws MongoException {
        userInfoRepository.save(new UserInfo(userInfo.getUsername(), userInfo.getPassword(), userInfo.getRoles()));
        return new ResponseUserInfo(userInfo.getUsername(), userInfo.getPassword());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "success login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout() {
        System.out.println("logout");
        return "success logout";
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
