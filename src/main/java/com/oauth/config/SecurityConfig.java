package com.oauth.config;

import com.oauth.filter.MyFilter;
import com.oauth.mongo.repository.UserInfoRepository;
import com.oauth.provisioning.MongoUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.HeaderWriterFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable();
        http
                .authorizeRequests()
                .antMatchers("/oauth/**", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
        http
                .addFilterBefore(new MyFilter(), HeaderWriterFilter.class);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        MongoUserDetailsManager manager = new MongoUserDetailsManager(userInfoRepository);
        return manager;
    }

}

//http://localhost:8080/oauth/authorize?response_type=code&client_id=client&redirect_uri=http://localhost:8080

/**
 * 二者关系
 * WebSecurityConfigurerAdapter用于保护oauth相关的endpoints，同时主要作用于用户的登录(form login,Basic auth)
 * ResourceServerConfigurerAdapter用于保护oauth要开放的资源，同时主要作用于client端以及token的认证(Bearer auth)
 * 因此二者是分工协作的
 * <p>
 * 在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
 */
