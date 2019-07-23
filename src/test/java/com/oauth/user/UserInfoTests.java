package com.oauth.user;

import com.oauth.main.OAuthApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuthApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserInfoTests {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private UserInfo userInfo;

    @Test
    public void contextLoads() {
        userInfo.setPassword("23456");
        userInfo.getPassword();
    }

    @Test
    public void getUserInfo() {

    }

}
