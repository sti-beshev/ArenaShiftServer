package com.beshev.arenashiftserver.user;

public class AdminChangePassInfo {
	
	private String username;
	private String currentPassword;
	private String newPassword;

	public AdminChangePassInfo() {
		
	}
	
	public AdminChangePassInfo(String username, String currentPassword, String newPassword) {
		
		this.username = username;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
