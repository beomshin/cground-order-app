package com.kr.cground.persistence.repository;

import com.kr.cground.persistence.entity.StoresEntity;
import com.kr.cground.persistence.entity.enums.StoreStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoresRepository extends JpaRepository<StoresEntity, String> {

    Optional<StoresEntity> findByStoreIdAndIsActive(String storeOrderId, StoreStatus isActive);

    @Modifying
    @Query("update stores set orderCount = orderCount + 1 where storeId = :storeId")
    void updateOrderCount(@Param("storeId") String storeId);
}
