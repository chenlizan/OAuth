package com.oauth.user;

import com.oauth.main.OAuthApplication;
import com.oauth.mongo.entity.UserInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuthApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserInfoTests {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d", port);
        System.out.println(String.format("port is : [%d]", port));
        this.base = new URL(url);
    }

    @Test
    public void register() {
        UserInfo userInfo = new UserInfo("chenlizan", "123456", "15623078770");
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<UserInfo> httpEntity = new HttpEntity<UserInfo>(userInfo, headers);
        ResponseEntity<UserInfo> responseEntity = testRestTemplate.postForEntity(this.base.toString() + "/register", httpEntity, UserInfo.class);
        System.out.println(responseEntity);
    }
}
