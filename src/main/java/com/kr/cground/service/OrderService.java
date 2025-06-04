package com.kr.cground.service;

import com.kr.cground.dto.OrderRequest;
import com.kr.cground.persistence.entity.OrdersEntity;

public interface OrderService {

    boolean isActiveStore(String store_id);

    OrdersEntity addOrder(OrderRequest orderRequest);

    OrdersEntity getOrder(String orderNumber);
}
