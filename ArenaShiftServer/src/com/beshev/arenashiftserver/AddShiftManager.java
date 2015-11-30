package com.beshev.arenashiftserver;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class AddShiftManager {

	public AddShiftManager() {
		
	}
	
	public boolean saveShift(Shift shift) {
		
		boolean status = false;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
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
		} catch (EntityNotFoundException e) {
			
			Entity dayEntity = new Entity("Day", shift.getDay(), monthKey);
			dayEntity.setProperty("panMehanik", shift.getPanMehanik());
			dayEntity.setProperty("panKasaOne", shift.getPanKasaOne());
			dayEntity.setProperty("panKasaTwo", shift.getPanKasaTwo());
			dayEntity.setProperty("panKasaThree", shift.getPanKasaThree());
			dayEntity.setProperty("razporeditelOne", shift.getRazporeditelOne());
			dayEntity.setProperty("razporeditelTwo", shift.getRazporeditelTwo());
			dayEntity.setProperty("cenMehanik", shift.getCenMehanik());
			dayEntity.setProperty("cenKasa", shift.getCenKasa());
			datastore.put(dayEntity);
			
			status = true;
		}
		
		return status;
	}

}
