package com.beshev.arenashiftserver.update;

public class UpdateRequest {
	
	private String userName;
	private long dbVersion;
	
	public UpdateRequest() {
		
	}

	public UpdateRequest(String userName, long dbVersion) {
		
		this.userName = userName;
		this.dbVersion = dbVersion;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getDbVersion() {
		return dbVersion;
	}

	public void setDbVersion(long dbVersion) {
		this.dbVersion = dbVersion;
	}

}
