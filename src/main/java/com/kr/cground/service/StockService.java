package com.kr.cground.service;

public interface StockService {

    boolean reserveStock(String productId, int quantity, String userId);
}
