package com.snz1.seatas.account.service;

import java.math.BigDecimal;

public interface AccountService {
    
  void debit(String userId, BigDecimal num);

}
