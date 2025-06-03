package com.kr.cground.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseResult {

    SUCESS("0000", "성공"),
    FAIL_ORDER("4001", "주문실패")

    ;

    private String code;
    private String message;
}
