package com.application.webhookEvents;

public class PullRequestReviewCommentEvent extends BaseEvent {
	private PullRequest pull_request;
	private Comment comment;

	public PullRequest getPullRequest(){
		return pull_request;
	}

	public void setPullRequest(PullRequest pull_request){
		this.pull_request = pull_request;
	}

	public Comment getComment(){
		return comment;
	}

	public void setComment(Comment comment){
		this.comment = comment;
	}
}
