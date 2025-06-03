package com.kr.cground.controller;

import com.kr.cground.constants.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ResponseResult result = ResponseResult.BAD_PARAM;
        return ResponseEntity.badRequest().body(Map.of(
                "resultCode", result.getCode(),
                "resultMsg", ex.getBindingResult().getFieldError().getDefaultMessage()
        ));
    }
}
