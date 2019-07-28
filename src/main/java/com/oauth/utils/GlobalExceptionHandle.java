package com.oauth.utils;

import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandle {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        MongoWriteException cause = (MongoWriteException) e.getCause();
        if (cause.getCode() == 11000) {
            return new ResponseEntity(e, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String handleCustomException(CustomException e) {
        System.out.println(e);
        return "";
    }

}
