package com.application.webhookEvents;

public class Comment {

	private String path;
	private User user;
	private String body;
	private String created_at;
	private String updated_at;
	private String html_url;

	public String getPath(){
		return path;
	}

	public void setPath(String path){
		this.path = path;
	}

	public User getUser(){
		return user;
	}

	public void setUser(User user){
		this.user = user;
	}

	public String getBody(){
		return body;
	}

	public void setBody(String body){
		this.body = body;
	}

	public String getCreatedAt(){
		return created_at;
	}

	public void setCreatedAt(String created_at){
		this.created_at = created_at;
	}

	public String getUpdatedAt(){
		return updated_at;
	}

	public void setUpdatedAt(String updated_at){
		this.updated_at = updated_at;
	}

	public String getHtmlUrl(){
		return html_url;
	}

	public void setHtmlUrl(String html_url){
		this.html_url = html_url;
	}
}
