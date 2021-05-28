package com.application.webhookEvents;

public class PullRequest {

	private String html_url;
	private String state;
	private String title;
	private String created_at;
	private String updated_at;
	private String closed_at;
	private String merged_at;
	private Boolean draft;
	private User merged_by;
	private Integer comments;
	private Integer review_comments;
	private Integer commits;
	private Integer changed_files;
	private User user;

	public String getHtmlUrl(){
		return html_url;
	}

	public void setHtmlUrl(String html_url){
		this.html_url = html_url;
	}
	public String getState(){
		return this.state;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getTitle(){
		return this.title;
	}

	public void setTitle() {
		this.title = title;
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

	public String getClosedAt(){
		return closed_at;
	}

	public void setClosedAt(String closed_at){
		this.closed_at = closed_at;
	}

	public String getMergedAt(){
		return merged_at;
	}

	public void setMergedAt(String merged_at){
		this.merged_at = merged_at;
	}

	public Boolean getDraft(){
		return draft;
	}

	public void setDraft(Boolean draft){
		this.draft = draft;
	}

	public User getMergedBy(){
		return this.merged_by;
	}

	public void setMergedBy(User merged_by){
		this.merged_by = merged_by;
	}

	public Integer getComments(){
		return comments;
	}

	public void setComments(Integer comments){
		this.comments = comments;
	}

	public Integer getReviewComments(){
		return review_comments;
	}

	public void setReviewComments(Integer review_comments){
		this.review_comments = review_comments;
	}

	public Integer getCommits(){
		return commits;
	}

	public void setCommits(Integer commits){
		this.commits = commits;
	}

	public Integer getChangedFiles(){
		return changed_files;
	}

	public void setChangedFiles(Integer changed_files){
		this.changed_files = changed_files;
	}

	public User getUser(){
		return user;
	}

	public void setUser(User user){
		this.user = user;
	}
}
