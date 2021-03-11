package com.snz1.seatas.account.api;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;

import com.snz1.seatas.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public void debit(@RequestParam String userId, @RequestParam BigDecimal orderMoney) {
        log.info("account XID = {}", RootContext.getXID());
        accountService.debit(userId, orderMoney);
    }

}
