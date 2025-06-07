package com.kr.cground.service;

public interface StockService {

    boolean reserveStock(String productId, int quantity, String userId);

    void releaseStock(String productId, String userId);

    void confirmOrder(String productId, String userId);
}
