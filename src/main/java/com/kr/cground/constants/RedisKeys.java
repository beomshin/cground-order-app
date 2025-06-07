package com.kr.cground.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKeys {

    STOCK_KEY("stock:%s"),
    LOCK_KEY("lock:%s")
    ;

    private final String key;
}
