package com.snz1.seatas.order.client;

import java.math.BigDecimal;

import gateway.api.EnvelopeResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccountClient {

  @POST("moneis/{account_id}/debit")
  @EnvelopeResponse
  @FormUrlEncoded
  Void debit(
    @Path("account_id") String account_id,
    @Field("money") BigDecimal money
  );

}

