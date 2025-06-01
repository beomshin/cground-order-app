package com.kr.cground.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest {

    private String buyer;
    
    private List<ItemRequest> items;
}
