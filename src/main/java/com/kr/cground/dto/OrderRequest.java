package com.kr.cground.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest {

    @NotBlank(message = "접수번호가 누락되었습니다.")
    private String receiptNumber;

    @NotNull(message = "금액이 누락되었습니다.")
    private Integer totalAmount;

    @NotBlank(message = "유저번호가 누락되었습니다.")
    private String userId;

    @NotBlank(message = "가맹점번호가 누락되었습니다.")
    private String storeId;

    private String currency = "KRW";

    private String memo;

    @NotNull(message = "주문정보가 누락되었습니다.")
    @Size(min = 1, message = "주문정보가 누락되었습니다.")
    private List<ItemRequest> items;


}
