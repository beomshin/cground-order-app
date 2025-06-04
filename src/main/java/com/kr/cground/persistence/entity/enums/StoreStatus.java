package com.kr.cground.persistence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreStatus {

    NON_ACTIVE(0),
    ACTIVE(1),

    ;

    private int code;

    public static StoreStatus fromCode(int code) {
        for (StoreStatus s : StoreStatus.values()) {
            if (s.getCode() == code) {
                return s;
            }
        }
        return null;
    }
}
