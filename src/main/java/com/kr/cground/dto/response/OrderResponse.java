package com.kr.cground.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kr.cground.persistence.entity.OrdersEntity;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponse {

    private String receiptNumber;

    private String orderNumber;

    private String userId;

    private String storeId;

    private String transcationId;

    private String orderStatus;

    private String paymentStatus;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp orderDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp paymentDate;

    private Integer totalAmount;

    private String paymentMethod;

    private String memo;

    private List<ItemResponse> items;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    private static class ItemResponse {
        private String itemNumber;
        private String productName;
        private Integer quantity;
        private Integer unitPrice;
        private Integer totalPrice;
    }


    public static OrderResponse from(OrdersEntity ordersEntity) {
        List<ItemResponse> items = ordersEntity.getItems().stream().map(
                it -> ItemResponse.builder()
                        .itemNumber(it.getItemNumber())
                        .productName(it.getProductName())
                        .quantity(it.getQuantity())
                        .unitPrice(it.getUnitPrice())
                        .totalPrice(it.getTotalPrice())
                        .build()
        ).toList();

        return OrderResponse.builder()
                .receiptNumber(ordersEntity.getReceiptNumber())
                .orderNumber(ordersEntity.getOrderNumber())
                .userId(ordersEntity.getUserId())
                .storeId(ordersEntity.getStoreId())
                .transcationId(ordersEntity.getTranscationId())
                .orderStatus(ordersEntity.getOrderStatus().getCode())
                .paymentStatus(ordersEntity.getPaymentStatus().getCode())
                .orderDate(ordersEntity.getOrderDate())
                .paymentDate(ordersEntity.getPaymentDate())
                .totalAmount(ordersEntity.getTotalAmount())
                .paymentMethod(ordersEntity.getPaymentMethod())
                .memo(ordersEntity.getMemo())
                .items(items)
                .build();
    }

}
