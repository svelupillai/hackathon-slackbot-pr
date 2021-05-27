package com.application.webhookEvents;

public class PullRequestEvent extends BaseEvent {

	private int number;
	private PullRequest pull_request;

	public int getNumber(){
		return number;
	}

	public void setNumber(int number){
		this.number = number;
	}

	public PullRequest getPullRequest(){
		return pull_request;
	}

	public void setPullRequest(PullRequest pull_request){
		this.pull_request = pull_request;
	}
}
