package com.snz1.seatas.order.client;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

// 库存服务接口
public interface StorageClient {

  @POST("inventories/{commodityCode}/deduct")
  @FormUrlEncoded
  Void deduct(
    @Path("commodityCode") String commodityCode,
    @Field("count") Integer count
  );

}
