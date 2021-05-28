package com.application.webhookEvents;

public class PullRequestReviewCommentEvent extends BaseEvent {
	private Comment comment;

	public Comment getComment(){
		return comment;
	}

	public void setComment(Comment comment){
		this.comment = comment;
	}
}
