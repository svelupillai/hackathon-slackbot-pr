package com.application;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class WebhookService {

	@Autowired
	WebClient webClient;


	public void createWebhook(String owner, String repo) {

	}
}
