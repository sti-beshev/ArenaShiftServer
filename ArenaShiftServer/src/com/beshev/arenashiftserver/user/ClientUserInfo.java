package com.beshev.arenashiftserver.user;

public class ClientUserInfo {
	
	private String username;
	private String clientUsername;
	private String clientPassword;
	private String activationCode;
	
	public ClientUserInfo() {
		
	}

	public ClientUserInfo(String username, String clientUsername, String clientPassword, String activationCode) {
		
		this.username = username;
		this.clientUsername = clientUsername;
		this.clientPassword = clientPassword;
		this.activationCode = activationCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getClientUsername() {
		return clientUsername;
	}

	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}

	public String getClientPassword() {
		return clientPassword;
	}

	public void setClientPassword(String clientPassword) {
		this.clientPassword = clientPassword;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
}
