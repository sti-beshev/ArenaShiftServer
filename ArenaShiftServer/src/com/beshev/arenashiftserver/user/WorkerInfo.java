package com.beshev.arenashiftserver.user;

public class WorkerInfo {
	
	private String username;
	private String label;
	private boolean isWorking;

	public WorkerInfo() {}

	public WorkerInfo(String username, String label, boolean isWorking) {

		this.username = username;
		this.label = label;
		this.isWorking = isWorking;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

}
