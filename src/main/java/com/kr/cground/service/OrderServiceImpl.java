package com.kr.cground.service;

import com.kr.cground.constants.ResponseResult;
import com.kr.cground.dto.request.OrderRequest;
import com.kr.cground.exception.OrderException;
import com.kr.cground.persistence.entity.OrdersEntity;
import com.kr.cground.persistence.entity.enums.OrderStatus;
import com.kr.cground.persistence.entity.enums.StoreStatus;
import com.kr.cground.persistence.entity.enums.UseStatus;
import com.kr.cground.persistence.repository.OrderRepository;
import com.kr.cground.persistence.repository.StoresRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StockService stockService;
    private final StoresRepository storesRepository;

    @Override
    @Transactional
    public OrdersEntity addOrder(OrderRequest orderRequest) throws OrderException {
        log.info("주문 등록 시작");
        var storesEntity = storesRepository.findByStoreIdAndIsActive(orderRequest.getStoreId(), StoreStatus.ACTIVE);

        if (storesEntity.isEmpty() || storesEntity.get().getIsActive() == StoreStatus.NON_ACTIVE) {
            throw new OrderException(ResponseResult.NOT_EXIST_STORE);
        }

        var orderNumber= UUID.randomUUID().toString().replace("-", "").substring(0, 32);

        if (UseStatus.ON == UseStatus.fromCode(orderRequest.getStockFlag())) {

            var restock = orderRequest.getItems().stream()
                    .filter(it -> stockService.reserveStock(orderRequest.getStoreId() + ":" + it.getItemNumber(), it.getQuantity(), orderNumber))
                    .toList();

            if (restock.size() != orderRequest.getItems().size()) {
                restock.forEach(it -> stockService.releaseStock(orderRequest.getStoreId() + ":" + it.getItemNumber(), it.getQuantity(), orderNumber));
                throw new OrderException(ResponseResult.NOT_EXIST_STOCK);
            }

        }

        var orders = OrdersEntity.builder()
                .receiptNumber(orderRequest.getReceiptNumber())
                .orderStatus(OrderStatus.CREATED)
                .orderNumber(orderNumber)
                .userId(orderRequest.getUserId())
                .storeId(orderRequest.getStoreId())
                .totalAmount(orderRequest.getTotalAmount())
                .currency(orderRequest.getCurrency())
                .stockFlag(UseStatus.fromCode(orderRequest.getStockFlag()))
                .memo(orderRequest.getMemo())
                .build();

        var orderItems = orderRequest.getItems().stream().map(it -> it.mapToEntity(orders)).toList();
        orders.setItems(orderItems);

        storesRepository.updateOrderCount(orders.getStoreId());

        log.info("주문 등록 종료");
        return orderRepository.save(orders);
    }

    @Override
    public OrdersEntity getOrder(String orderNumber) throws OrderException {
        var order = orderRepository.findByOrderNumber(orderNumber);

        if (order.isEmpty()) {
            throw new OrderException(ResponseResult.NOT_EXIST_ORDER);
        }

        return order.get();
    }

}
