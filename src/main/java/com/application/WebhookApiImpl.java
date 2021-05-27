package com.application;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.application.webhookEvents.BaseEvent;
import com.application.webhookEvents.EventAction;
import com.application.webhookEvents.PullRequestEvent;
import com.google.gson.Gson;

@Controller
public class WebhookApiImpl {

	@RequestMapping(value = "/payload",
			produces = { "application/json" },
			consumes = { "application/json" },
			method = RequestMethod.POST)
	public ResponseEntity<String> payload(HttpEntity<String> httpEntity) {
		// do some magic!
		String json = httpEntity.getBody();
		EventAction action = new Gson().fromJson(json, EventAction.class);
		BaseEvent evnt;
		switch (action.getAction()){
			case "assigned":
			case "auto_merge_disabled":
			case "auto_merge_enabled":
			case "closed":
			case "converted_to_draft":
			case "edited":
			case "labeled":
			case "locked":
			case "opened":
			case "ready_for_review":
			case "reopened":
			case "review_request_removed":
			case "review_requested":
			case "synchronize":
			case "unassigned":
			case "unlabeled":
			case "unlocked":
				evnt = new Gson().fromJson(json, PullRequestEvent.class);
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + action.getAction());
		}
		System.out.println(evnt);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
