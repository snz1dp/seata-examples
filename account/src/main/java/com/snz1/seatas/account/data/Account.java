package com.snz1.seatas.account.data;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("account_tbl")
@ApiModel("账户")
@Data
public class Account implements Serializable {

  private static final long serialVersionUID = -310579695580466813L;

  @ApiModelProperty("账户ID")
  @TableId
  private String account_id;

  @ApiModelProperty("账户金额")
  private BigDecimal account_money;
    
}
