package com.kr.cground.service;

import com.kr.cground.persistence.entity.OrderTb;
import com.kr.cground.persistence.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public boolean addOrder(OrderTb orderTb) {
        orderRepository.save(orderTb);
        return false;
    }
}
