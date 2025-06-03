package com.kr.cground.persistence.repository;

import com.kr.cground.persistence.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {

    OrdersEntity findByOrderNumber(String receiptNumber);
}
