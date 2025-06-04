package com.kr.cground.persistence.repository;

import com.kr.cground.persistence.entity.StoreOrderStatusEntity;
import com.kr.cground.persistence.entity.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreOrderStatusRepository extends JpaRepository<StoreOrderStatusEntity, String> {

    Optional<StoreOrderStatusEntity> findByStoreIdAndIsActive(String storeOrderId, StoreStatus isActive);

}
