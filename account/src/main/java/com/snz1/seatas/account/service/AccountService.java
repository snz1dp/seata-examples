package com.snz1.seatas.account.service;

import java.math.BigDecimal;

import com.snz1.seatas.account.data.Account;

public interface AccountService {
    
  void debit(String userId, BigDecimal num);

  Account get(String userId);

}
