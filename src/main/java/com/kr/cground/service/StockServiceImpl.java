package com.kr.cground.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String STOCK_KEY = "stock:%s"; // 제품 재고 개수 관리
    private static final String LOCK_KEY = "lock:%s"; // 예약 개수 관리
    private static final long TTL_SECONDS = 300; // 재고 예약 TTL 5분


    @Override
    public boolean reserveStock(String productId, int quantity, String userId) {
        String key = String.format(STOCK_KEY, productId);
        String lockKey = String.format(LOCK_KEY, productId + ":" + userId);

        return Boolean.TRUE.equals(redisTemplate.execute(conn -> {
            conn.multi();

            Long cureentStock = Long.parseLong(redisTemplate.opsForValue().get(key));
            if (cureentStock < quantity) {
                conn.discard();
                return false;
            }


        }));
    }
}
