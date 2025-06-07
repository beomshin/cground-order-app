package com.kr.cground.persistence.entity.converter;

import com.kr.cground.persistence.entity.enums.UseStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UseStatusConverter implements AttributeConverter<UseStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UseStatus useStatus) {
        return useStatus.getCode();
    }

    @Override
    public UseStatus convertToEntityAttribute(Integer integer) {
        return UseStatus.fromCode(integer);
    }
}
