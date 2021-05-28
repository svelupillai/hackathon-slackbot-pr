package com.application;

import com.database.Controller.DatabaseController;
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
	static DatabaseController dbController = new DatabaseController();
	private SlackApp slackApp = new SlackApp();

	@RequestMapping(value = "/payload",
			produces = { "application/json" },
			consumes = { "application/json" },
			method = RequestMethod.POST)
	public ResponseEntity<String> payload(HttpEntity<String> httpEntity) throws IOException, SlackApiException {
		String json = httpEntity.getBody();
		BaseEvent evnt = null;
		JSONObject jsonObject = new JSONObject(json);
		String type="";
		String username = "";
		String link = null;
		boolean shouldBeSameUser = false;
		boolean sendToOwner = false;
		if (jsonObject.has("number")) {
			evnt = new Gson().fromJson(json, PullRequestEvent.class);
			type = "pull request";
			if(evnt.getAction() == "review_requested"){
				System.out.println("Skipping this");
				return new ResponseEntity<String>(HttpStatus.OK);
			}
			shouldBeSameUser = false;
			sendToOwner = !evnt.getPullRequest().getUser().getLogin().equals(evnt.getUser().getLogin());
			username = evnt.getPullRequest().getUser().getLogin();

		}
		if (jsonObject.has("comment")) {
			System.out.println("JSONN"+ json);
			evnt = new Gson().fromJson(json, PullRequestReviewCommentEvent.class);
			type = "pull request comment";
			username = evnt.getPullRequest().getUser().getLogin();
			PullRequestReviewCommentEvent prComment = (PullRequestReviewCommentEvent)evnt;
			link = prComment.getComment().getHtmlUrl();
			shouldBeSameUser = true;
		}
		if (jsonObject.has("review")) {
			evnt = new Gson().fromJson(json, PullRequestReviewEvent.class);
			type = "pull request review";
			username = evnt.getPullRequest().getUser().getLogin();
			shouldBeSameUser = true;
		}
		if(evnt == null ) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		link = link == null ? evnt.getPullRequest().getHtmlUrl() : link;
		var users = dbController.getInterestedUsers(username, evnt.getRepository().getFullName(), shouldBeSameUser, sendToOwner);
		System.out.println("IDS: " +users);
		Ping.pingNotification(users, slackApp, type, evnt, link);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
