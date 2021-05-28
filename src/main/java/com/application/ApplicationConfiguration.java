package com.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfiguration {
	private static final String WEBHOOK_BASE_URL = "https://api.github.com";
	@Bean
	public WebClient myWebClient(WebClient.Builder webClientBuilder) {
		return webClientBuilder
				.baseUrl(WEBHOOK_BASE_URL)
				.build();
	}
}
