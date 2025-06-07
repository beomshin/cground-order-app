package com.kr.cground.exception;

import com.kr.cground.constants.ResponseResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class OrderException extends Exception {

    private final ResponseResult result;
}
