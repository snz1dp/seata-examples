package com.snz1.seatas.order.client;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gateway.api.RetrofitUtils;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Response;
import retrofit2.Retrofit;

// 使用retrofit2配置服务接口
@Slf4j
@Configuration
public class ClientConfig {
    
  private static final String AccountRetrofitID = "accountRetrofit";

  private static final String StorageRetrofitID = "storageRetrofit";

	// 账户服务地址
  @Value("${app.account.url:http://localhost:9191/seatas/example/account}")
	private String accountServUrl;

	// 库存服务地址
  @Value("${app.storage.url:http://localhost:9190/seatas/example/storage}")
	private String storageServUrl;

	// JWT授权令牌
	@Value("${app.client.jwt.token:${app.jwt.token:}}")
	private String jwtAppToken;

	// JWT私钥(RSA)
	@Value("${app.client.jwt.private_key:${app.jwt.private_key:}}")
	private String jwtPrivateKey;

	// JWT过期时间
	@Value("${app.client.jwt.live_time:${app.jwt.live_time:1800}}")
	private Integer jwtTokenLiveSeconds;

  public ClientConfig() {
		log.info("初始化账户及库存客户端...");
  }

  @Bean(name = AccountRetrofitID)
	public Retrofit accountRetrofit() {
		return this.createRetrofit(this.accountServUrl, jwtAppToken, jwtPrivateKey, jwtTokenLiveSeconds);
	}

	// 创建Retrofit对象
  private Retrofit createRetrofit(String service_url, String jwtAppToken2, String jwtPrivateKey2,
			Integer jwtTokenLiveSeconds2) {
		if (!StringUtils.endsWith(service_url, "/"))
			service_url += "/";
		
		// 添加请求拦截器
		return RetrofitUtils.createRetrofit(service_url, jwtAppToken, jwtPrivateKey, jwtTokenLiveSeconds, //
			new Interceptor() {

				@Override
				public Response intercept(Chain chain) throws IOException {
					String xid = RootContext.getXID();
					if (StringUtils.isEmpty(xid)) {
						return chain.proceed(chain.request());
					}
					// 传播分布式事务ID至其他微服务服务接口
					okhttp3.Request.Builder new_req_builder = chain.request().newBuilder();
					new_req_builder.removeHeader(RootContext.KEY_XID).addHeader(RootContext.KEY_XID, xid);
					return chain.proceed(new_req_builder.build());
				}

			}
		);
	}

	@Bean(name = StorageRetrofitID)
	public Retrofit storageRetrofit() {
		return this.createRetrofit(this.storageServUrl, jwtAppToken, jwtPrivateKey, jwtTokenLiveSeconds);
	}

	@Bean
	public AccountClient accountClient(@Qualifier(AccountRetrofitID) Retrofit retrofit) {
		return retrofit.create(AccountClient.class);
	}

	@Bean
	public StorageClient storageClient(@Qualifier(StorageRetrofitID) Retrofit retrofit) {
		return retrofit.create(StorageClient.class);
	}

}
