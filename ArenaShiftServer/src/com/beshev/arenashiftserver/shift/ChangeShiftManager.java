package com.beshev.arenashiftserver.shift;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.update.ChangeManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ChangeShiftManager {
	
	private DatastoreService datastore;
	
	public ChangeShiftManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	public ServerResponseMessage<String> changeShift(Shift shift) {
		
		Key monthKey = new KeyFactory.Builder("Year", shift.getYear()).addChild("Month", shift.getMonth()).getKey();
		
		Entity dayEntity = new Entity("Day", shift.getDay(), monthKey);
		dayEntity.setProperty("Year", shift.getYear());
		dayEntity.setProperty("Month", shift.getMonth());
		dayEntity.setProperty("Day", shift.getDay());
		dayEntity.setProperty("panMehanik", shift.getPanMehanik());
		dayEntity.setProperty("panKasaOne", shift.getPanKasaOne());
		dayEntity.setProperty("panKasaTwo", shift.getPanKasaTwo());
		dayEntity.setProperty("panKasaThree", shift.getPanKasaThree());
		dayEntity.setProperty("razporeditelOne", shift.getRazporeditelOne());
		dayEntity.setProperty("razporeditelTwo", shift.getRazporeditelTwo());
		datastore.put(dayEntity);
		
		ChangeManager changeManager = new ChangeManager();
		changeManager.addChange(dayEntity.getKey());
		
		
		return new ServerResponseMessage<>("The shift is corrected", false, null);
	}

}
