package com.kr.cground.dto.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {

    private String resultCode;
    private String resultMsg;
    private String transactionId;
    private String paymentDate;
    private String paymentMethod;

}
