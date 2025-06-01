package com.kr.cground.dto;

import com.kr.cground.persistence.entity.ItemTb;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequest {

    private String name;

    private Integer count;

    private Integer amount;

    private Integer price;

    public ItemTb mapToEntity() {

        return ItemTb.builder()
                .name(name)
                .count(count)
                .amount(amount)
                .price(price)
                .build();
    }
}
