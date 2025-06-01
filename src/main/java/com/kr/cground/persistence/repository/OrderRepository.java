package com.kr.cground.persistence.repository;

import com.kr.cground.persistence.entity.OrderTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderTb, Long> {

}
