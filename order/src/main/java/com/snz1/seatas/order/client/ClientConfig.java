package com.snz1.seatas.order.client;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gateway.api.RetrofitUtils;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Retrofit;

@Slf4j
@Configuration
public class ClientConfig {
    
  private static final String AccountRetrofitID = "accountRetrofit";

  private static final String StorageRetrofitID = "storageRetrofit";

  @Value("${app.account.url:http://localhost:9191/seatas/example/account}")
	private String accountServUrl;

  @Value("${app.storage.url:http://localhost:9190/seatas/example/storage}")
	private String storageServUrl;

	@Value("${app.client.jwt.token:${app.jwt.token:}}")
	private String jwtAppToken;

	@Value("${app.client.jwt.private_key:${app.jwt.private_key:}}")
	private String jwtPrivateKey;

	@Value("${app.client.jwt.live_time:${app.jwt.live_time:1800}}")
	private Integer jwtTokenLiveSeconds;

  public ClientConfig() {
		log.info("初始化账户及库存客户端...");
  }

  @Bean(name = AccountRetrofitID)
	public Retrofit accountRetrofit() {
		String service_url = this.accountServUrl;
		if (!StringUtils.endsWith(service_url, "/"))
			service_url += "/";

		return RetrofitUtils.createRetrofit(service_url, jwtAppToken, jwtPrivateKey, jwtTokenLiveSeconds);
	}

  @Bean(name = StorageRetrofitID)
	public Retrofit storageRetrofit() {
		String service_url = this.storageServUrl;
		if (!StringUtils.endsWith(service_url, "/"))
			service_url += "/";

		return RetrofitUtils.createRetrofit(service_url, jwtAppToken, jwtPrivateKey, jwtTokenLiveSeconds);
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
