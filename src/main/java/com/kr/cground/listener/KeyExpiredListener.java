package com.kr.cground.listener;

import com.kr.cground.constants.RedisKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Slf4j
public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = new String(message.getBody());

        if (expiredKey.contains("lock")) {
            String[] keys = expiredKey.split(":");
            String storeId = keys[1];
            String itemNumber = keys[2];
            String quantity = keys[3];

            String key = String.format(RedisKeys.STOCK_KEY.getKey(), storeId + ":" + itemNumber);
            redisTemplate.opsForValue().increment(key, Integer.parseInt(quantity));
        }

    }
}
