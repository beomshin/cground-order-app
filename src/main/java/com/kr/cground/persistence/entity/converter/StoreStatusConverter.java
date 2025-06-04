package com.kr.cground.persistence.entity.converter;

import com.kr.cground.persistence.entity.enums.StoreStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StoreStatusConverter implements AttributeConverter<StoreStatus, Integer> {


    @Override
    public Integer convertToDatabaseColumn(StoreStatus storeStatus) {
        return storeStatus.getCode();
    }

    @Override
    public StoreStatus convertToEntityAttribute(Integer s) {
        return StoreStatus.fromCode(s);
    }
}
