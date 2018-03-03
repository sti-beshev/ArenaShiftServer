package com.beshev.arenashiftserver.update;

import java.util.ArrayList;

import com.beshev.arenashiftserver.shift.Shift;

public class UpdateClientManager {

	private UpdateRequest updateRequest;
	private ChangeManager changeManager;
	
	/*  Този клас не се тества защото е прост. Ако бъде променен трябва да се напише тест.*/
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
