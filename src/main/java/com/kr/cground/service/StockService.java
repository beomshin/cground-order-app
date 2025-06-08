package com.kr.cground.service;

public interface StockService {

    boolean reserveStock(String productId, int quantity, String userId);

    void releaseStock(String productId, int quantity, String orderNumber);

    void confirmOrder(String productId, int quantity, String orderNumber);
}
