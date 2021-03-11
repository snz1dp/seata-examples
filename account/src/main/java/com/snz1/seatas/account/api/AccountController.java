package com.snz1.seatas.account.api;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

import com.snz1.seatas.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(path = "moneis")
public class AccountController {

  @Autowired
  AccountService accountService;

  @PostMapping(value = "{userId}")
  public void debit(@PathVariable("userId") String userId, @RequestParam BigDecimal orderMoney) {
    log.info("account XID = {}", RootContext.getXID());
    accountService.debit(userId, orderMoney);
  }

}
