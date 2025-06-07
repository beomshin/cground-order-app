package com.kr.cground.dto;

import com.kr.cground.persistence.entity.OrderItemsEntity;
import com.kr.cground.persistence.entity.OrdersEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequest {

    private String productName;

    private String itemId;

    private Integer quantity;

    private Integer unitPrice;

    public OrderItemsEntity mapToEntity(OrdersEntity entity) {
        return OrderItemsEntity.builder()
                .productName(productName)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .totalPrice(unitPrice * quantity)
                .orderId(entity)
                .build();
    }

}
