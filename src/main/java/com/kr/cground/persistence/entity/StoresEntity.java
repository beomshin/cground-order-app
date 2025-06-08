package com.kr.cground.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kr.cground.persistence.entity.common.BaseEntity;
import com.kr.cground.persistence.entity.converter.StoreStatusConverter;
import com.kr.cground.persistence.entity.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Getter
@SuperBuilder
@ToString(callSuper=true)
@Entity(name = "stores")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class StoresEntity extends BaseEntity {

    @Id
    @Column(name = "store_id")
    private String storeId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "order_count")
    private Integer orderCount;

    @Column(name = "is_active")
    @Convert(converter = StoreStatusConverter.class)
    private StoreStatus isActive;

    @Column(name = "last_ordered_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp lastOrderedAt;

}
