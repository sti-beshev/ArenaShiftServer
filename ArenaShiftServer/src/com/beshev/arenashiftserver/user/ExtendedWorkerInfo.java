package com.beshev.arenashiftserver.user;

public class ExtendedWorkerInfo extends WorkerInfo {
	
	private String activationCode;
	
	public ExtendedWorkerInfo(String username, String label, String activationCode, boolean isWorking) {

		this.username = username;
		this.label = label;
		this.activationCode = activationCode;
		this.isWorking = isWorking;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

}
