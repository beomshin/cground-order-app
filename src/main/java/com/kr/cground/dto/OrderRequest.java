package com.kr.cground.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest {

    private String receiptNumber;

    private Integer totalAmount;

    private String userId;

    private String currency;

    private String memo;
    
    private List<ItemRequest> items;

}
