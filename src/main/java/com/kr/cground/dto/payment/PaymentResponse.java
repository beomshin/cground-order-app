package com.kr.cground.dto.payment;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentResponse {

    private String resultCode;
    private String resultMsg;
    private String transactionId;
    private String paymentDate;
    private String paymentMethod;

}
