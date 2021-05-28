package com.application;

public class LastResponse {

	public void setCode(String code) {
		this.code = code;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	private String code;
	private String status;
	private String message;
}
