package com.kr.cground.persistence.repository;

import com.kr.cground.persistence.entity.StoresEntity;
import com.kr.cground.persistence.entity.enums.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoresRepository extends JpaRepository<StoresEntity, String> {

    Optional<StoresEntity> findByStoreIdAndIsActive(String storeOrderId, StoreStatus isActive);

}
