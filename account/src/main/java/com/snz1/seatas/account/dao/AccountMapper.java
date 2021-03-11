package com.snz1.seatas.account.dao;

import com.snz1.seatas.account.data.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    
}
