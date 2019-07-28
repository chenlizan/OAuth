package com.oauth.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private long code;
    private String msg;

    public CustomException(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
