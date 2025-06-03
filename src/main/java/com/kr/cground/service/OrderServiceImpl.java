package com.kr.cground.service;

import com.kr.cground.dto.OrderRequest;
import com.kr.cground.persistence.entity.OrderItemsEntity;
import com.kr.cground.persistence.entity.OrdersEntity;
import com.kr.cground.persistence.entity.enums.OrderStatus;
import com.kr.cground.persistence.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrdersEntity addOrder(OrderRequest orderRequest) {
        var orders = OrdersEntity.builder()
                .receiptNumber(orderRequest.getReceiptNumber())
                .orderStatus(OrderStatus.CREATED)
                .orderNumber(UUID.randomUUID().toString().replace("-", "").substring(0, 32))
                .userId(orderRequest.getUserId())
                .storeId(orderRequest.getStoreId())
                .totalAmount(orderRequest.getTotalAmount())
                .build();

        List<OrderItemsEntity> orderItems = orderRequest.getItems().stream().map(it -> it.mapToEntity(orders)).toList();

        orders.setItems(orderItems);

        return orderRepository.save(orders);
    }

}
