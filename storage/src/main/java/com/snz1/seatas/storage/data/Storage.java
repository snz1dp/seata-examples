package com.snz1.seatas.storage.data;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("商品库存")
@TableName("storage_tbl")
@Data
public class Storage implements Serializable {

  private static final long serialVersionUID = -8764302385524740895L;

  @TableId
  @ApiModelProperty("商品代码")
  private String commodity_code;

  @ApiModelProperty("库存数量")
  private Integer count;

}
