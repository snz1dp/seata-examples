package com.snz1.seatas.account.data;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("account_tbl")
@ApiModel("账户")
@Data
public class Account {

  @ApiModelProperty("账户ID")
  private String account_id;

  @ApiModelProperty("账户金额")
  private BigDecimal account_money;
    
}
