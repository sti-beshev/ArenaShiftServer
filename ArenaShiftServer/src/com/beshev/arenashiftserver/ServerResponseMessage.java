package com.beshev.arenashiftserver;

public class ServerResponseMessage<T> {
	
	private String message;
	private boolean isError;
	private T payload;
	
	public ServerResponseMessage() {
		
	}

	public ServerResponseMessage(String message, boolean isError, T payload) {
		
		this.message = message;
		this.isError = isError;
		this.payload = payload;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

}
