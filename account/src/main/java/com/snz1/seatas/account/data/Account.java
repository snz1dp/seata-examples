package com.snz1.seatas.account.data;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@TableName("account_tbl")
@ApiModel("账户")
@Data
public class Account {

  private String account_id;

  private Integer account_money;
    
}
