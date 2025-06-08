package com.kr.cground.service;

import com.kr.cground.dto.payment.PaymentRequest;
import com.kr.cground.dto.payment.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Value("${payment.url}")
    private String paymentUrl;

    @Override
    public Optional<PaymentResponse> addPayment(PaymentRequest paymentRequest) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // 요청 매개변수 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PaymentRequest> request = new HttpEntity<>(paymentRequest, headers);

            ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(paymentUrl, request, PaymentResponse.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return Optional.of(response.getBody());
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }


}
