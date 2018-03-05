package com.beshev.arenashiftserver.update;

import java.util.ArrayList;

import com.beshev.arenashiftserver.shift.Shift;

public class UpdateClientManager {

	private UpdateRequest updateRequest;
	private ChangeManager changeManager;
	
	// This class is not tested because is too simple. If you changed - add tests.
	public UpdateClientManager(UpdateRequest updateRequest) {
		
		this.updateRequest = updateRequest;
		changeManager = new ChangeManager();
	}
	
	public UpdateResponse getUpdateResponse () {
		
		// This is a empty response to return if there are no changes.
		UpdateResponse updateResponse = new UpdateResponse(0, new ArrayList<Shift>());
		
		if (((Long)changeManager.getLastChange().getProperty("changeVersion")) > updateRequest.getDbVersion()) {
			
			updateResponse = changeManager.getShiftList(updateRequest.getDbVersion());
		}
		
		return updateResponse;
	}

}
