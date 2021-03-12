package com.snz1.seatas.account.service;

import com.snz1.seatas.account.data.Account;
import com.snz1.seatas.account.dao.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

  private static final String ERROR_USER_ID = "1002";

  @Autowired
  private AccountMapper accountMapper;

  @Override
  public void debit(String userId, BigDecimal num) {
    Account account = accountMapper.selectById(userId);
    account.setAccount_money(account.getAccount_money().subtract(num));
    accountMapper.updateById(account);
    if (ERROR_USER_ID.equals(userId)) {
      throw new RuntimeException("account branch exception");
    }
  }

  @Override
  public Account get(String userId) {
    return accountMapper.selectById(userId);
  }

}
