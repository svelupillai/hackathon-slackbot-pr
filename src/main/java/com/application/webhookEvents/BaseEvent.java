package com.application.webhookEvents;

public abstract class BaseEvent {

	private String action;
	private User sender;
	private Repository repository;
	private Object organization;
	private Object installation;
	private PullRequest pull_request;

	public String getAction(){
		return action;
	}

	public void setAction(String action){
		this.action = action;
	}

	public User getUser(){
		return sender;
	}

	public void setUser(User sender){
		this.sender = sender;
	}

	public Repository getRepository(){
		return repository;
	}

	public PullRequest getPullRequest(){ return pull_request; }

	public void setRepository(Repository repository){
		this.repository = repository;
	}

	public Object getOrganization(){
		return organization;
	}

	public void setOrganization(Object organization){
		this.organization = organization;
	}

	public Object getInstallation(){
		return installation;
	}

	public void setInstallation(Object installation){
		this.installation = installation;
	}

	public void setPullRequest(PullRequest html_url){
		this.pull_request = pull_request;
	}

}
