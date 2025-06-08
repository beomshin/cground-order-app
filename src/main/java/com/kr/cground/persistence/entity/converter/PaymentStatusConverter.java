package com.kr.cground.persistence.entity.converter;

import com.kr.cground.persistence.entity.enums.PaymentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String> {


    @Override
    public String convertToDatabaseColumn(PaymentStatus paymentStatus) {
        return paymentStatus.getCode();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String s) {
        return PaymentStatus.valueOf(s);
    }

}
