package com.kr.cground.service;

import com.kr.cground.constants.RedisKeys;
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
    private static final long TTL_SECONDS = 5; // 재고 예약 TTL 5분


    @Override
    public boolean reserveStock(String productId, int quantity, String orderNumber) {
        String key = String.format(RedisKeys.STOCK_KEY.getKey(), productId);
        String lockKey = String.format(RedisKeys.LOCK_KEY.getKey(), productId + ":" + quantity + ":" + orderNumber);

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
     * @param orderNumber
     */
    @Override
    public void releaseStock(String productId, int quantity, String orderNumber) {
        String lockKey = String.format(RedisKeys.LOCK_KEY.getKey(), productId + ":" + quantity + ":" + orderNumber);
        String stockKey = String.format(RedisKeys.STOCK_KEY.getKey(), productId);

        String reserved = redisTemplate.opsForValue().get(lockKey); // 예약키 조회
        if (reserved != null) {
            redisTemplate.delete(lockKey); // 키 삭제
            redisTemplate.opsForValue().increment(stockKey, quantity); // 완료하지않은 예약 종료로 재고 상승
        }

    }

    /**
     * 결제 확정
     * @param productId
     * @param userId
     */
    @Override
    public void confirmOrder(String productId, String userId) {
        String lockKey = String.format(RedisKeys.LOCK_KEY.getKey(), productId + ":" + userId);
        redisTemplate.delete(lockKey); // 재고 확정, 임시예약 삭제
    }

}
