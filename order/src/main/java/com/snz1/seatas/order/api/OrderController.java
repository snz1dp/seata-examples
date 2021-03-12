package com.snz1.seatas.order.api;

import com.snz1.seatas.order.service.OrderService;
import io.seata.core.context.RootContext;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "debit")
public class OrderController {

  @Autowired
  OrderService orderService;

  @ApiOperation("商品下单")
  @PostMapping
  public void debit(
    @ApiParam("账户ID，可用值：1001、1002，使用1002时会抛出异常")
    @RequestParam String account_id,
    @ApiParam("商品代码，可用值：2001")
    @RequestParam String commodity_code,
    @ApiParam("下单数量")
    @RequestParam Integer count
  ) {
    log.info("order XID: {}", RootContext.getXID());
    orderService.create(account_id, commodity_code, count);
  }

}
