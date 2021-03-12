package com.snz1.seatas.order.service;

import com.snz1.seatas.order.client.AccountClient;
import com.snz1.seatas.order.client.StorageClient;
import io.seata.spring.annotation.GlobalTransactional;

import com.snz1.seatas.order.data.Order;
import com.snz1.seatas.order.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

  @Autowired
  private AccountClient accountClient;

  @Autowired
  private StorageClient storageClient;

  @Autowired
  private OrderMapper orderMapper;

  @GlobalTransactional
  public void create(String accountId, String commodityCode, Integer count) {
    BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
    Order order = new Order();
    order.setAccount_id(accountId);
    order.setCommodity_code(commodityCode);
    order.setCount(count);
    order.setMoney(orderMoney);

    orderMapper.insert(order);
    accountClient.debit(accountId, orderMoney);
    storageClient.deduct(commodityCode, count);
  }

}
