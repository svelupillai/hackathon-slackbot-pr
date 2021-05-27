package com.application;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.application.webhookEvents.BaseEvent;
import com.application.webhookEvents.PullRequestEvent;
import com.application.webhookEvents.PullRequestReviewCommentEvent;
import com.google.gson.Gson;

@Controller
public class WebhookApiImpl {

	@RequestMapping(value = "/payload",
			produces = { "application/json" },
			consumes = { "application/json" },
			method = RequestMethod.POST)
	public ResponseEntity<String> payload(HttpEntity<String> httpEntity) {
		String json = httpEntity.getBody();
		BaseEvent evnt = null;
		JSONObject jsonObject = new JSONObject(json);

		if (jsonObject.has("number"))
			evnt = new Gson().fromJson(json, PullRequestEvent.class);
		if (jsonObject.has("comment"))
			evnt = new Gson().fromJson(json, PullRequestReviewCommentEvent.class);

		if (evnt != null)
			System.out.println(evnt);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
