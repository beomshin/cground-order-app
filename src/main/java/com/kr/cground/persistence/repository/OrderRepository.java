package com.kr.cground.persistence.repository;

import com.kr.cground.persistence.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {

    Optional<OrdersEntity> findByOrderNumber(String receiptNumber);
}
