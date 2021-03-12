package com.snz1.seatas.order.api;

import com.snz1.seatas.order.service.OrderService;
import io.seata.core.context.RootContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "debit")
public class OrderController {

  @Autowired
  OrderService orderService;

  @PostMapping
  public void debit(@RequestParam String account_id, @RequestParam String commodity_code, @RequestParam Integer count) {
    System.out.println("order XID " + RootContext.getXID());
    orderService.create(account_id, commodity_code, count);
  }

}
