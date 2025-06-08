package com.kr.cground.controller;

import com.kr.cground.constants.ResponseResult;
import com.kr.cground.exception.OrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleValidationExceptions(Exception ex) {
        log.error("", ex);
        ResponseResult result = ResponseResult.FAIL_REQUEST;
        return ResponseEntity.internalServerError().body(Map.of(
                "resultCode", result.getCode(),
                "resultMsg", result.getMessage()
        ));
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<?> handleValidationExceptions(OrderException ex) {
        log.error("주문 오류 발생 : {}, {}", ex.getResult().getCode(), ex.getResult().getMessage());
        return ResponseEntity.ok().body(Map.of(
                "resultCode", ex.getResult().getCode(),
                "resultMsg", ex.getResult().getMessage()
        ));
    }
}
