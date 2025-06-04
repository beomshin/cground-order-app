package com.kr.cground.service;

import com.kr.cground.dto.OrderRequest;
import com.kr.cground.persistence.entity.OrderItemsEntity;
import com.kr.cground.persistence.entity.OrdersEntity;
import com.kr.cground.persistence.entity.enums.OrderStatus;
import com.kr.cground.persistence.entity.enums.StoreStatus;
import com.kr.cground.persistence.repository.OrderRepository;
import com.kr.cground.persistence.repository.StoreOrderStatusRepository;
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
    private final StoreOrderStatusRepository storeOrderStatusRepository;

    @Override
    public boolean isActiveStore(String store_id) {
        return storeOrderStatusRepository.findByStoreIdAndIsActive(store_id, StoreStatus.ACTIVE).isPresent();
    }

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
                .currency(orderRequest.getCurrency())
                .memo(orderRequest.getMemo())
                .build();

        var orderItems = orderRequest.getItems().stream().map(it -> it.mapToEntity(orders)).toList();

        orders.setItems(orderItems);

        var storeOrderStatus = storeOrderStatusRepository.findById(orderRequest.getStoreId()).orElse(null);

        storeOrderStatus.addOrder();

        return orderRepository.save(orders);
    }

    @Override
    public OrdersEntity getOrder(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

}
