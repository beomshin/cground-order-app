package com.kr.cground.order;

import com.kr.cground.dto.ItemRequest;
import com.kr.cground.dto.OrderRequest;
import com.kr.cground.exception.OrderException;
import com.kr.cground.persistence.entity.StoresEntity;
import com.kr.cground.persistence.repository.StoresRepository;
import com.kr.cground.service.OrderService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@ActiveProfiles("test")
public class ConcurrencyControlTest {


    private static final Logger log = LoggerFactory.getLogger(ConcurrencyControlTest.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private StoresRepository storesRepository;


    private final int THREAD_COUNT = 10;

    @Test
    @Transactional
    void 동시에_재고_감소_요청을_보내면_정상적으로_처리되어야_한다() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        AtomicInteger count = new AtomicInteger();

        OrderRequest request = OrderRequest.builder()
                .storeId("ghHotel")
                .receiptNumber(UUID.randomUUID().toString().replace("-","").substring(0, 16))
                .userId("테스트유저")
                .totalAmount(4000)
                .items(Arrays.asList(
                        ItemRequest.builder()
                                .productName("상품1")
                                .quantity(1)
                                .unitPrice(1000)
                                .build(),
                        ItemRequest.builder()
                                .productName("상품2")
                                .quantity(2)
                                .unitPrice(1000)
                                .build(),
                        ItemRequest.builder()
                                .productName("상품3")
                                .quantity(2)
                                .unitPrice(500)
                                .build()
                ))
                .build();

        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.submit(() -> {
                try {
                    count.getAndIncrement();
                    orderService.addOrder(request);
                } catch (OrderException e) {} finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 종료될 때까지 대기

        Optional<StoresEntity> entity =  storesRepository.findById(request.getStoreId());

        if (entity.isPresent()) {
            int remainingStock = entity.get().getOrderCount();
            log.info("동시성 제어 개수 : {} / {}", remainingStock ,count.get());
            assert 90 != remainingStock;
        }
    }
}
