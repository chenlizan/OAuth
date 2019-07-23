package com.oauth.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    @RequestMapping(method = RequestMethod.GET, name = "/getUserInfo")
    public UserInfo getUserInfo() {
        return new UserInfo("chenlizan", "123456");
    }
}
