package com.application;

import java.util.Date;
import java.util.List;

public class WebhookResponse {
	public void setType(String type) {
		this.type = type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTest_url(String test_url) {
		this.test_url = test_url;
	}

	public void setPing_url(String ping_url) {
		this.ping_url = ping_url;
	}

	public void setLast_response(LastResponse last_response) {
		this.last_response = last_response;
	}

	public String getType() {
		return type;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isActive() {
		return active;
	}

	public List<String> getEvents() {
		return events;
	}

	public Config getConfig() {
		return config;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public String getUrl() {
		return url;
	}

	public String getTest_url() {
		return test_url;
	}

	public String getPing_url() {
		return ping_url;
	}

	public LastResponse getLast_response() {
		return last_response;
	}

	private String type;
	private String id;
	private String name;
	private boolean active;
	private List<String> events;
	private Config config;
	private Date updated_at;
	private Date created_at;
	private String url;
	private String test_url;
	private String ping_url;
	private LastResponse last_response;

}
