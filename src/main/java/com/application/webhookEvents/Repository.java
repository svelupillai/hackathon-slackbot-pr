package com.application.webhookEvents;

public class Repository {

	private String name;
	private String full_name;

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getFullName(){
		return full_name;
	}

	public void setFullName(String full_name){
		this.full_name = full_name;
	}
}
