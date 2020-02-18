package com.oauth.user;

import com.oauth.main.OAuthApplication;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.test.BeforeOAuth2Context;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URL;
import java.util.LinkedHashMap;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuthApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserInfoTests {

    @LocalServerPort
    private int port;

    private URL base;

    private String access_token;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setUp() throws Exception {
        String url = String.format("http://localhost:%d", port);
        System.out.println(String.format("port is : [%d]", port));
        this.base = new URL(url);
    }

    @Before
    public void getAccessToken() throws Exception {
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("client_id", "client");
        multiValueMap.add("client_secret", "secret");
        multiValueMap.add("grant_type", "password");
        multiValueMap.add("username", "chenlizan");
        multiValueMap.add("password", "123456");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multiValueMap, headers);
        ResponseEntity responseEntity = testRestTemplate.postForEntity(this.base.toString() + "/oauth/token", httpEntity, Object.class);
        Assert.assertEquals(responseEntity.getStatusCode().getReasonPhrase(), responseEntity.getStatusCode(), HttpStatus.OK);
        this.access_token = ((LinkedHashMap) responseEntity.getBody()).get("access_token").toString();
    }

    @Test
    public void register() {
        UserInfoDTO userInfoDTO = new UserInfoDTO("chenlizan", "123456", new String[]{"USER"});
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        HttpEntity<UserInfoDTO> httpEntity = new HttpEntity<UserInfoDTO>(userInfoDTO, headers);
        ResponseEntity<UserInfoVO> responseEntity = testRestTemplate.postForEntity(this.base.toString() + "/register", httpEntity, UserInfoVO.class);
        Assert.assertEquals(responseEntity.getStatusCode().getReasonPhrase(), responseEntity.getStatusCode(), HttpStatus.OK);
        System.out.println(responseEntity);
    }

    @Test
    public void updateUserInfo() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setNickname("clz");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        headers.setBearerAuth(this.access_token);
        HttpEntity<UserInfoDTO> httpEntity = new HttpEntity<UserInfoDTO>(userInfoDTO, headers);
        ResponseEntity<UserInfoVO> responseEntity = testRestTemplate.postForEntity(this.base.toString() + "/api/updateUserInfo", httpEntity, UserInfoVO.class);
        Assert.assertEquals(responseEntity.getStatusCode().getReasonPhrase(), responseEntity.getStatusCode(), HttpStatus.OK);
        System.out.println(responseEntity);
    }

    @Test
    public void referApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        headers.setBearerAuth(this.access_token);
        headers.add("Ncc-Cookie", "nccloudsessionid:1234567890");
        HttpEntity httpEntity = new HttpEntity(headers);
        ResponseEntity<String> responseEntity = testRestTemplate.exchange(this.base.toString() + "/api/referApi", HttpMethod.GET, httpEntity, String.class);
        Assert.assertEquals(responseEntity.getStatusCode().getReasonPhrase(), responseEntity.getStatusCode(), HttpStatus.OK);
        System.out.println(responseEntity);
    }

}
