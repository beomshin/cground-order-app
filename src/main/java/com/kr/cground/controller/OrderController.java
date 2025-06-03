package com.kr.cground.controller;

import com.kr.cground.constants.ResponseResult;
import com.kr.cground.dto.OrderRequest;
import com.kr.cground.persistence.entity.OrdersEntity;
import com.kr.cground.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
            ordersEntity = orderService.addOrder(request);
        } catch (Exception e) {
            result = ResponseResult.FAIL_ORDER;
        }

        return ResponseEntity.ok(Map.of(
                "orderNumber", ordersEntity.getOrderNumber(),
                "resultCode", result.getCode(),
                "resultMsg", result.getMessage()
        ));
    }
}
