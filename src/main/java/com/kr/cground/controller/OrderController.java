package com.kr.cground.controller;

import com.kr.cground.constants.ResponseResult;
import com.kr.cground.dto.OrderRequest;
import com.kr.cground.dto.OrderResponse;
import com.kr.cground.persistence.entity.OrdersEntity;
import com.kr.cground.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/cground/order")
    public ResponseEntity<?> addOrder(
            @Valid  @RequestBody OrderRequest request
    ) {
        ResponseResult result = ResponseResult.SUCESS;

        OrdersEntity ordersEntity = null;
        try {

            if (orderService.isActiveStore(request.getStoreId())) {
                ordersEntity = orderService.addOrder(request);
            } else {
                result = ResponseResult.NOT_EXIST_STORE;
            }

        } catch (Exception e) {
            log.error("Exception occured", e);
            result = ResponseResult.FAIL_ORDER;
        }

        return ResponseEntity.ok(Map.of(
                "orderNumber", ordersEntity == null ? "" : ordersEntity.getOrderNumber(),
                "resultCode", result.getCode(),
                "resultMsg", result.getMessage()
        ));
    }

    @GetMapping("/cground/order/{orderNumber}")
    public ResponseEntity<?> getOrder(@PathVariable String orderNumber) {
        ResponseResult result = ResponseResult.SUCESS;

        OrdersEntity ordersEntity = null;
        try {
            ordersEntity = orderService.getOrder(orderNumber);
            if (ordersEntity == null) {
                result = ResponseResult.NOT_EXIST_ORDER;
            }
        } catch (Exception e) {
            log.error("Exception occured", e);
            result = ResponseResult.FAIL_FIND_ORDER;
        }

        return ResponseEntity.ok(Map.of(
                "resultCode", result.getCode(),
                "resultMsg", result.getMessage(),
                "order", ordersEntity == null ? "" : OrderResponse.from(ordersEntity)
        ));
    }
}
