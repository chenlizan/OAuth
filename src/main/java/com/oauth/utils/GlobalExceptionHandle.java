package com.oauth.utils;

import com.mongodb.MongoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandle {

    @ExceptionHandler(MongoException.class)
    public ResponseEntity handleMongoException(MongoException e) {
        if (e.getCode() == 11000) {
            return new ResponseEntity(e, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleException(CustomException e) {
        return new ResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
