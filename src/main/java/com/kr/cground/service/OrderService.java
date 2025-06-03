package com.kr.cground.service;

import com.kr.cground.dto.OrderRequest;
import com.kr.cground.persistence.entity.OrdersEntity;

public interface OrderService {

    OrdersEntity addOrder(OrderRequest orderRequest);

}
