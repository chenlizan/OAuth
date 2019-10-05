package com.oauth.user;

import com.mongodb.MongoException;
import com.oauth.mongo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "Hello Spring Security";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public UserInfoVO register(@RequestBody UserInfoDTO userInfoDTO) throws MongoException {
        UserInfo userInfoPO = userInfoService.save(new UserInfo(userInfoDTO.getUsername(), userInfoDTO.getPassword(), userInfoDTO.getRoles()));
        return new UserInfoVO(userInfoPO);
    }

    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public String getCode(@RequestParam(required = false, value = "code") String code) {
        return code;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "success login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
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
