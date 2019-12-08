package com.oauth.config;

import io.ipfs.api.IPFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IPFSConfig {

    @Bean
    public IPFS IPFSInstance() {
        return new IPFS("/ip4/127.0.0.1/tcp/5001");
    }

}
