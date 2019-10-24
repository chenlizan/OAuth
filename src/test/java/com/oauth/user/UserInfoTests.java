package com.oauth.user;

import com.oauth.main.OAuthApplication;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
    public void getAccessToken() throws Exception {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("username", "chenlizan");
        multiValueMap.add("password", "123456");
        multiValueMap.add("grant_type", "password");
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");
        headers.setContentType(type);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multiValueMap, headers);
        testRestTemplate.postForEntity(this.base.toString() + "/oauth/token", httpEntity, UserInfoVO.class);
//        System.out.println(responseEntity);
    }

    @Test
    public void register() {
        UserInfoDTO userInfoDTO = new UserInfoDTO("chenlizan", "123456", new String[]{"USER"});
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<UserInfoDTO> httpEntity = new HttpEntity<UserInfoDTO>(userInfoDTO, headers);
        ResponseEntity<UserInfoVO> responseEntity = testRestTemplate.postForEntity(this.base.toString() + "/register", httpEntity, UserInfoVO.class);
        System.out.println(responseEntity);
    }

    @Test
    public void updateUserInfo() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setNickname("clz");
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<UserInfoDTO> httpEntity = new HttpEntity<UserInfoDTO>(userInfoDTO, headers);
        ResponseEntity<UserInfoVO> responseEntity = testRestTemplate.postForEntity(this.base.toString() + "/updateUserInfo", httpEntity, UserInfoVO.class);
        System.out.println(responseEntity);
    }

}
