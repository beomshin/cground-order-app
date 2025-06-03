package com.kr.cground.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseResult {

    SUCESS("0000", "성공"),
    FAIL_ORDER("4001", "주문실패"),
    FAIL_FIND_ORDER("4002", "주문조회실패"),
    NOT_EXIST_ORDER("4003", "미등록주문"),
    BAD_PARAM("3001", "파라미터 오류")

    ;

    private String code;
    private String message;
}
