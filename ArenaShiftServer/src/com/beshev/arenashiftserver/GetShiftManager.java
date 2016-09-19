package com.beshev.arenashiftserver;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class GetShiftManager {

	public GetShiftManager() {
		
	}
	
	public Shift getShift(ShiftDate shiftDate) {
		
		/* Важно е да е null защото ако не намери смяна ще върне, null който е нужен
		 * на клиента за да знае че няма таква смяна. */
		Shift shift = null;
		
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
			shift.setCenMehanik((String)shiftEntity.getProperty("cenMehanik"));
			shift.setCenKasa((String)shiftEntity.getProperty("cenKasa"));
		
		} catch (EntityNotFoundException e) {
		
		}
		
		return shift;
	}

}
