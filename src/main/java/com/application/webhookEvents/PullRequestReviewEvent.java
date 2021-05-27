package com.application.webhookEvents;

public class PullRequestReviewEvent extends BaseEvent {
	private PullRequest pull_request;
	private Review review;

	public PullRequest getPullRequest(){

		return pull_request;
	}

	public void setPullRequest(PullRequest pull_request){
		this.pull_request = pull_request;
	}

	public Review getReview(){
		return review;
	}

	public void setReview(Review review){
		this.review = review;
	}
}
