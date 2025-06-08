package com.kr.cground.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    CREATED("CREATED"),
    PAID("PAID"),
    FAIL("FAIL"),
    ;

    private final String code;

}
