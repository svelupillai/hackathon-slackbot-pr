package com.application;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateWebhookRequest {
	private String name;
	private Config config;
	private List<String> events;
	private boolean active;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Config getConfig() {
		return config;
	}

	public List<String> getEvents() {
		return events;
	}

	public boolean isActive() {
		return active;
	}
}
