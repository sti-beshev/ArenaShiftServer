package com.beshev.arenashiftserver.shift;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.update.ChangeManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class AddShiftManager {
	
	DatastoreService datastore;

	public AddShiftManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	public ServerResponseMessage<String> saveShift(Shift shift) {
		
		boolean haveError = false;
		String status = "";
		
		Key yearKey = KeyFactory.createKey("Year", shift.getYear());
		
		try {
			datastore.get(yearKey);
		} catch (EntityNotFoundException e) {
			Entity yearEntity = new Entity("Year", shift.getYear());
			datastore.put(yearEntity);
		}
		
		Key monthKey = new KeyFactory.Builder("Year", shift.getYear()).addChild("Month", shift.getMonth()).getKey();
		
		try {
			datastore.get(monthKey);
		} catch (EntityNotFoundException e) {
			Entity monthEntity = new Entity("Month", shift.getMonth(), yearKey);
			datastore.put(monthEntity);
		}
		
		Key dayKey = new KeyFactory.Builder("Year", shift.getYear())
			.addChild("Month", shift.getMonth())
			.addChild("Day", shift.getDay())
			.getKey();
		
		try {
			
			datastore.get(dayKey);
			
			status = "This day already have a shift";
			haveError = true;
			
		} catch (EntityNotFoundException e) {
			
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
			changeManager.addChange(dayKey);
			
			status = " The shift is added";
		}
		
		return new ServerResponseMessage<>(status, haveError, null);
	}
	
	

}
