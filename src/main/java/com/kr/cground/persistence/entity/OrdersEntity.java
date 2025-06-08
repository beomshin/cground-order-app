package com.kr.cground.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kr.cground.constants.ResponseResult;
import com.kr.cground.dto.payment.PaymentResponse;
import com.kr.cground.persistence.entity.common.BaseEntity;
import com.kr.cground.persistence.entity.converter.OrderStatusConverter;
import com.kr.cground.persistence.entity.converter.PaymentStatusConverter;
import com.kr.cground.persistence.entity.converter.UseStatusConverter;
import com.kr.cground.persistence.entity.enums.OrderStatus;
import com.kr.cground.persistence.entity.enums.PaymentStatus;
import com.kr.cground.persistence.entity.enums.UseStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Getter
@SuperBuilder
@ToString(callSuper=true)
@Entity(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
public class OrdersEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "receipt_number")
    private String receiptNumber;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "transaction_id")
    private String transcationId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "store_id")
    private String storeId;

    @Setter
    @Column(name = "order_status")
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus orderStatus;

    @Setter
    @Column(name = "payment_status")
    @Convert(converter = PaymentStatusConverter.class)
    private PaymentStatus paymentStatus;


    @Column(name = "order_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp orderDate;

    @Column(name = "payment_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp paymentDate;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "stock_flag")
    @Convert(converter = UseStatusConverter.class)
    private UseStatus stockFlag;

    @Column(name = "currency")
    private String currency;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "memo")
    private String memo;

    @Setter
    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItemsEntity> items;

    public void setPaymentData(Optional<PaymentResponse> response) {
        if (response.isEmpty()) {
            this.paymentStatus = PaymentStatus.FAILED;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(response.get().getPaymentDate(), formatter);
            this.paymentDate = Timestamp.valueOf(localDateTime);
        } catch (Exception e) {
            this.paymentDate = this.orderDate;
        }

        this.transcationId = response.get().getTransactionId();
        this.paymentMethod = response.get().getPaymentMethod();

        if (response.get().getResultCode().equals(ResponseResult.SUCESS.getCode())){
            this.paymentStatus = PaymentStatus.SUCCESS;
        } else {
            this.paymentStatus = PaymentStatus.FAILED;
        }


    }

}
