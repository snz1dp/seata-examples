package com.snz1.seatas.order.data;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("订单")
@TableName("order_tbl")
@Data
public class Order implements Serializable {
  
  private static final long serialVersionUID = 7264877446460590152L;

  @ApiModelProperty("订单ID")
  @TableId(type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty("账户ID")
  private String account_id;

  @ApiModelProperty("商品代码")
  private String commodity_code;

  @ApiModelProperty("商品数量")
  private Integer count;

  @ApiModelProperty("金额")
  private BigDecimal money;

}
