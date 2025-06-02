package com.kr.cground.controller;

import com.kr.cground.dto.ItemRequest;
import com.kr.cground.dto.OrderRequest;
import com.kr.cground.persistence.entity.OrderItemsEntity;
import com.kr.cground.persistence.entity.OrdersEntity;
import com.kr.cground.persistence.entity.enums.OrderStatus;
import com.kr.cground.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/cground/order")
    public ResponseEntity<?> addOrder(
            @RequestBody OrderRequest request
    ) {
        OrdersEntity orders = OrdersEntity.builder()
                .receiptNumber(request.getReceiptNumber())
                .orderStatus(OrderStatus.CREATED)
                .orderNumber(UUID.randomUUID().toString().replace("-", "").substring(0, 32))
                .userId(request.getUserId())
                .totalAmount(request.getTotalAmount())
                .build();

        List<OrderItemsEntity> orderItems = request.getItems().stream().map(it -> it.mapToEntity(orders)).toList();

        orders.setItems(orderItems);

        OrdersEntity ordersEntity = orderService.addOrder(orders);

        return ResponseEntity.ok(Map.of(
                "orderNumber", ordersEntity.getOrderNumber(),
                "resultCode", "0000",
                "resultMsg", "주문성공"
        ));
    }
}
