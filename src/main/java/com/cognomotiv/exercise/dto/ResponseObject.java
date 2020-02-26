package com.cognomotiv.exercise.dto;

public class ResponseObject {
	
	private String message;
	private Object data;
	private String token;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message=message;
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data=data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token=token;
	}
}
