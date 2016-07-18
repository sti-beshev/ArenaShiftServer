package com.beshev.arenashiftserver;

import java.util.ArrayList;

public class UpdateClientManager {

	private UpdateRequest updateRequest;
	private ChangeManager changeManager;
	
	public UpdateClientManager(UpdateRequest updateRequest) {
		
		this.updateRequest = updateRequest;
		changeManager = new ChangeManager();
	}
	
	public UpdateResponse getUpdateResponse () {
		
		// Това е празния response, който ще бъде върнат ако няма промени.
		UpdateResponse updateResponse = new UpdateResponse(0, new ArrayList<Shift>());
		
		if (((Long)changeManager.getLastChange().getProperty("changeVersion")) > updateRequest.getDbVersion()) {
			
			updateResponse = changeManager.getShiftList(updateRequest.getDbVersion());
		}
		
		return updateResponse;
	}

}
