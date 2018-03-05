package com.beshev.arenashiftserver.shift;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class GetShiftManager {

	public GetShiftManager() {
		
	}
	
	public ServerResponseMessage<Shift> getShift(ShiftDate shiftDate) {
		
		Shift shift = null;
		
		boolean haveError = false;
		String status = "";
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Key dayKey = new KeyFactory.Builder("Year", shiftDate.getYear())
		.addChild("Month", shiftDate.getMonth())
		.addChild("Day", shiftDate.getDay())
		.getKey();
	
	
		try {
		
			Entity shiftEntity = datastore.get(dayKey);
			shift = new Shift();
			shift.setYear(shiftDate.getYear());
			shift.setMonth(shiftDate.getMonth());
			shift.setDay(shiftDate.getDay());
			shift.setPanMehanik((String)shiftEntity.getProperty("panMehanik"));
			shift.setPanKasaOne((String)shiftEntity.getProperty("panKasaOne"));
			shift.setPanKasaTwo((String)shiftEntity.getProperty("panKasaTwo"));
			shift.setPanKasaThree((String)shiftEntity.getProperty("panKasaThree"));
			shift.setRazporeditelOne((String)shiftEntity.getProperty("razporeditelOne"));
			shift.setRazporeditelTwo((String)shiftEntity.getProperty("razporeditelTwo"));
		
		} catch (EntityNotFoundException e) {
			
			haveError = true;
			status = "No shift for this day";
		
		}
		
		return new ServerResponseMessage<>(status, haveError, shift);
	}

}
