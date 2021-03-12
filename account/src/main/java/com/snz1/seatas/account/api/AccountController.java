package com.snz1.seatas.account.api;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

import com.snz1.seatas.account.data.Account;
import com.snz1.seatas.account.service.AccountService;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gateway.api.Result;
import gateway.api.Return;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping(path = "moneis")
public class AccountController {

  @Autowired
  AccountService accountService;

  @GetMapping(value = "{userId}")
  public Return<Account> get(@PathVariable("userId") String userId) {
    log.info("account XID = {}", RootContext.getXID());
    Account acc = accountService.get(userId);
    Validate.notNull(acc);
    return Return.wrap(acc);
  }

  @PostMapping(value = "{userId}/debit")
  public Result debit(@PathVariable("userId") String userId, @RequestParam BigDecimal money) {
    log.info("account XID = {}", RootContext.getXID());
    accountService.debit(userId, money);
    return Return.success();
  }

}
