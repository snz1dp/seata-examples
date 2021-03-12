package com.snz1.seatas.order.service;

public interface OrderService {
    
  void create(String accountId, String commodityCode, Integer count);
  
}
