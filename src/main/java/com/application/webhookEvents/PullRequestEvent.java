package com.application.webhookEvents;

public class PullRequestEvent extends BaseEvent {

	private int number;

	public int getNumber(){
		return number;
	}

	public void setNumber(int number){
		this.number = number;
	}

}
