package com.application;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Config {
	public void setUrl(String url) {
		this.url = url;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public void setInsecure_ssl(String insecure_ssl) {
		this.insecure_ssl = insecure_ssl;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getUrl() {
		return url;
	}

	public String getContent_type() {
		return content_type;
	}

	public String getInsecure_ssl() {
		return insecure_ssl;
	}

	public String getToken() {
		return token;
	}

	public String getDigest() {
		return digest;
	}

	private String url;
	private String content_type;
	private String insecure_ssl;
	private String token;
	private String digest;
}
