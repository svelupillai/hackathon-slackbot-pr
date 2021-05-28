package com.application.webhookEvents;

public class Review {

	private User user;
	private String submitted_at;
	private String state;
	private String html_url;
	private String body;

	public User getUser(){
		return user;
	}

	public void setUser(User user){
		this.user = user;
	}

	public String getSubmittedAt(){
		return submitted_at;
	}

	public void setSubmittedAt(String submitted_at){
		this.submitted_at = submitted_at;
	}

	public String getState(){
		return this.state;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getHtmlUrl(){
		return html_url;
	}

	public void setHtmlUrl(String html_url){
		this.html_url = html_url;
	}

	public String getBody(){
		return body;
	}

	public void setBody(String body){
		this.body = body;
	}

}
