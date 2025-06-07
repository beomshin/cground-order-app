package com.kr.cground.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UseStatus {

    OFF(0),
    ON(1)
    ;

    private final int code;


    public static UseStatus fromCode(int code) {
        for (UseStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
