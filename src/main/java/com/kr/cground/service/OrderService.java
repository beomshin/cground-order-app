package com.kr.cground.service;

import com.kr.cground.dto.request.OrderRequest;
import com.kr.cground.exception.OrderException;
import com.kr.cground.persistence.entity.OrdersEntity;

public interface OrderService {

    OrdersEntity addOrder(OrderRequest orderRequest) throws OrderException;

    OrdersEntity getOrder(String orderNumber) throws OrderException;
}
