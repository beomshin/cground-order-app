package com.kr.cground.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String STOCK_KEY = "stock:%s"; // 제품 재고 개수 관리
    private static final String LOCK_KEY = "lock:%s"; // 예약 개수 관리
    private static final long TTL_SECONDS = 10; // 재고 예약 TTL 5분


    @Override
    public boolean reserveStock(String productId, int quantity, String orderNumber) {
        String key = String.format(STOCK_KEY, productId);
        String lockKey = String.format(LOCK_KEY, productId + ":" + orderNumber);

        return Boolean.TRUE.equals(redisTemplate.execute((RedisCallback<Boolean>) conn -> {
            conn.multi();

            long currentStock = Long.parseLong(redisTemplate.opsForValue().get(key));
            if (currentStock < quantity) {
                conn.discard();
                return false;
            }

            redisTemplate.opsForValue().decrement(key, quantity); // 재고 감소
            redisTemplate.opsForValue().set(lockKey, String.valueOf(quantity), TTL_SECONDS, TimeUnit.SECONDS); // 에약

            conn.exec();

            return true;
        }));
    }

    /**
     * 예약 종료
     * @param productId
     * @param userId
     */
    @Override
    public void releaseStock(String productId, String userId) {
        String lockKey = String.format(LOCK_KEY, productId + ":" + userId);
        String stockKey = String.format(STOCK_KEY, productId);

        String reserved = redisTemplate.opsForValue().get(lockKey); // 예약키 조회
        if (reserved != null) {
            redisTemplate.opsForValue().increment(stockKey, Long.parseLong(reserved)); // 완료하지않은 예약 종료로 재고 상승
            redisTemplate.delete(lockKey); // 키 삭제
        }
    }

    /**
     * 결제 확정
     * @param productId
     * @param userId
     */
    @Override
    public void confirmOrder(String productId, String userId) {
        String lockKey = String.format(LOCK_KEY, productId + ":" + userId);
        redisTemplate.delete(lockKey); // 재고 확정, 임시예약 삭제
    }

}
