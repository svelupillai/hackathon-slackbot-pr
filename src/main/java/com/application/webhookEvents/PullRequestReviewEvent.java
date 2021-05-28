package com.application.webhookEvents;

public class PullRequestReviewEvent extends BaseEvent {
	private Review review;

	public Review getReview(){
		return review;
	}

	public void setReview(Review review){
		this.review = review;
	}
}
