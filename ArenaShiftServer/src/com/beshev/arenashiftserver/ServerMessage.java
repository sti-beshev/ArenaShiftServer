package com.beshev.arenashiftserver;

public class ServerMessage {
	
	private String message;
	private boolean isError;

	public ServerMessage(String message) {
		
		this.message = message;
		this.isError = false;
	}

	public ServerMessage(String message, boolean isError) {
		
		this.message = message;
		this.isError = isError;
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

}
