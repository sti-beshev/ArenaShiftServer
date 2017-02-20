package com.beshev.arenashiftserver.update;

import java.util.List;

import com.beshev.arenashiftserver.shift.Shift;

public class UpdateResponse {
	
	private long dbVersion;
	private List<Shift> changesList;

	public UpdateResponse(long dbVersion, List<Shift> changesList) {
		super();
		this.dbVersion = dbVersion;
		this.changesList = changesList;
	}
	
	public long getDbVersion() {
		return dbVersion;
	}

	public void setDbVersion(long dbVersion) {
		this.dbVersion = dbVersion;
	}

	public List<Shift> getChangesList() {
		return changesList;
	}

	public void setChangesList(List<Shift> changesList) {
		this.changesList = changesList;
	}


}
