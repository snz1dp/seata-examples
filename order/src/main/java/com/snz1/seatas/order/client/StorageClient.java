package com.snz1.seatas.order.client;

import gateway.api.EnvelopeResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StorageClient {

  @POST("inventories/{commodityCode}/deduct")
  @EnvelopeResponse
  @FormUrlEncoded
  Void deduct(
    @Path("commodityCode") String commodityCode,
    @Field("count") Integer count
  );

}
