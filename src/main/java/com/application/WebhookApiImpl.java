package com.application;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
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
import com.application.webhookEvents.PullRequestReviewEvent;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

@Controller
public class WebhookApiImpl {

	static Map<String, String> env = System.getenv();
	static String value = env.get("SLACK_BOT_TOKEN");
	private SlackApp slackApp = new SlackApp();
	@RequestMapping(value = "/payload",
			produces = { "application/json" },
			consumes = { "application/json" },
			method = RequestMethod.POST)
	public ResponseEntity<String> payload(HttpEntity<String> httpEntity) throws IOException, SlackApiException {
		String json = httpEntity.getBody();
		BaseEvent evnt = null;
		JSONObject jsonObject = new JSONObject(json);

		if (jsonObject.has("number"))
			evnt = new Gson().fromJson(json, PullRequestEvent.class);
		if (jsonObject.has("comment"))
			evnt = new Gson().fromJson(json, PullRequestReviewCommentEvent.class);
		if (jsonObject.has("review"))
			evnt = new Gson().fromJson(json, PullRequestReviewEvent.class);

		if (evnt != null)
			System.out.println(evnt);

		ChatPostMessageResponse response = slackApp.initSlackApp().client().chatPostMessage(r -> r
				.token(value)
				.channel("U023EC7T3HA")
				.text(String.format(":wave: Your review is being requested on testing"))
		);


		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
