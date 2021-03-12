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
public class OrderServiceImpl implements OrderService {

  @Autowired
  private AccountClient accountClient;

  @Autowired
  private StorageClient storageClient;

  @Autowired
  private OrderMapper orderMapper;

  // 启用全局事务并命令
  @GlobalTransactional(timeoutMills = 300000, name = "snz1-seatas-order-example")
  public void create(String accountId, String commodityCode, Integer count) {
    BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
    Order order = new Order();
    order.setAccount_id(accountId);
    order.setCommodity_code(commodityCode);
    order.setCount(count);
    order.setMoney(orderMoney);

    orderMapper.insert(order);
    storageClient.deduct(commodityCode, count);
    accountClient.debit(accountId, orderMoney);
  }

}
