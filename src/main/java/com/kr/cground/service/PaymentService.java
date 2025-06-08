package com.kr.cground.service;

import com.kr.cground.dto.payment.PaymentRequest;
import com.kr.cground.dto.payment.PaymentResponse;

import java.util.Optional;

public interface PaymentService {

    Optional<PaymentResponse> addPayment(PaymentRequest paymentRequest);
}
