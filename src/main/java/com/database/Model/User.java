package com.database.Model;

import org.bson.codecs.pojo.annotations.BsonId;

import java.util.List;

public class User {
	@BsonId
	private String userId;
	private List<String> subscribedRepoIds;
	private List<String> subscribedUserIds;
	private int intervalTimeInMinutes;
	private String githubUsername;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getSubscribedRepoIds() {
		return subscribedRepoIds;
	}

	public void setSubscribedRepoIds(List<String> subscribedRepoIds) {
		this.subscribedRepoIds = subscribedRepoIds;
	}

	public List<String> getSubscribedUserIds() {
		return subscribedUserIds;
	}

	public void setSubscribedUserIds(List<String> subscribedUserIds) {
		this.subscribedUserIds = subscribedUserIds;
	}

	public int getIntervalTimeInMinutes() {
		return intervalTimeInMinutes;
	}

	public void setIntervalTimeInMinutes(int intervalTimeInMinutes) {
		this.intervalTimeInMinutes = intervalTimeInMinutes;
	}

	public String getGithubUsername() {
		return githubUsername;
	}

	public void setGithubUsername(String githubUsername) {
		this.githubUsername = githubUsername;
	}
}
