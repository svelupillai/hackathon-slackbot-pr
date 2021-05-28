package com.application;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class WebhookApiImpl {
	private static String token = "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";
	private static String userName = "katiaru";
	@Autowired
	private WebClient webClient;

	@RequestMapping(value = "/payload",
			produces = { "application/json" },
			consumes = { "application/json" },
			method = RequestMethod.POST)
	public ResponseEntity<String> payload(Object body) {
		System.out.println("OKKKK");
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/getAll/{owner}/{repo}",
			produces = { "application/json" },
			method = RequestMethod.GET)
	public ResponseEntity<List<String>> getAll(@PathVariable String owner, @PathVariable String repo) {
			List<String> listOfEvents = Arrays.stream(webClient
					.get()
					.uri(String.format("/repos/%s/%s/hooks", owner, repo))
					.header("Authorization", "Basic " + Base64Utils
							.encodeToString((userName + ":" + token).getBytes(UTF_8)))
					.retrieve()
					.bodyToMono(WebhookResponse[].class).block()).map(wh -> wh.getEvents()).flatMap(wh -> wh.stream()).collect(Collectors.toList());

		return new ResponseEntity<>(listOfEvents, HttpStatus.OK);
	}

	@RequestMapping(value = "/create/{owner}/{repo}",
			produces = { "application/json" },
			consumes = { "application/json" },
			method = RequestMethod.POST)
	public ResponseEntity<String> create(@PathVariable String owner, @PathVariable String repo, @RequestBody String event) {
		CreateWebhookRequest request = new CreateWebhookRequest();
		request.setActive(true);
		request.setName("web");
		request.setEvents(Collections.singletonList(event));
		Config config = new Config();
		config.setUrl("http://7b790788b44a.ngrok.io/payload");
		config.setContent_type("json");
		request.setConfig(config);
		Mono<HttpStatus> response = webClient
				.post()
				.uri(String.format("/repos/%s/%s/hooks", owner, repo))
				.header("Authorization", "Basic " + Base64Utils
						.encodeToString((userName + ":" + token).getBytes(UTF_8)))
				.body(Mono.just(request), CreateWebhookRequest.class)
				.exchange()
				.map(clientResponse -> clientResponse.statusCode());
		HttpStatus httpStatus = response.block();

		return new ResponseEntity<String>(httpStatus);
	}

}
