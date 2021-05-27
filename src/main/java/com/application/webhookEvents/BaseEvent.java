package com.application.webhookEvents;

public abstract class BaseEvent {

	private String action;
	private Sender sender;
	private Repository repository;
	private Object organization;
	private Object installation;

	public String GetAction(){
		return action;
	}

	public void SetAction(String action){
		this.action = action;
	}

	public Sender GetSender(){
		return sender;
	}

	public void SetSender(Sender sender){
		this.sender = sender;
	}

	public Repository GetRepository(){
		return repository;
	}

	public void SetRepository(Repository repository){
		this.repository = repository;
	}

	public Object GetOrganization(){
		return organization;
	}

	public void SetOrganization(Object organization){
		this.organization = organization;
	}

	public Object GetInstallation(){
		return installation;
	}

	public void SetInstallation(Object installation){
		this.installation = installation;
	}

}