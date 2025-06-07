package com.kr.cground.controller;

import com.kr.cground.constants.ResponseResult;
import com.kr.cground.dto.OrderRequest;
import com.kr.cground.dto.OrderResponse;
import com.kr.cground.exception.OrderException;
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
    ) throws OrderException {
        var result = ResponseResult.SUCESS;

        var ordersEntity = orderService.addOrder(request);

        return ResponseEntity.ok(Map.of(
                "orderNumber", ordersEntity.getOrderNumber(),
                "resultCode", result.getCode(),
                "resultMsg", result.getMessage()
        ));
    }

    @GetMapping("/cground/order/{orderNumber}")
    public ResponseEntity<?> getOrder(@PathVariable String orderNumber) throws OrderException {
        var result = ResponseResult.SUCESS;

        var ordersEntity = orderService.getOrder(orderNumber);

        return ResponseEntity.ok(Map.of(
                "resultCode", result.getCode(),
                "resultMsg", result.getMessage(),
                "order", OrderResponse.from(ordersEntity)
        ));
    }
}
