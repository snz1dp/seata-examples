package com.snz1.seatas.storage.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("商品库存")
@Data
public class Storage {

  @ApiModelProperty("唯一ID（自动生成")
  private Integer id;

  @ApiModelProperty("商品代码")
  private String commodityCode;

  @ApiModelProperty("库存数量")
  private Integer count;

}
