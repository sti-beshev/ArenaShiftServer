package com.beshev.arenashiftserver.update;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beshev.arenashiftserver.shift.Shift;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class ChangeManager {
	
	private DatastoreService datastore;

	public ChangeManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
	}
	
	public void addChange(Key dayKey) {
		
		Entity oldChange = getLastChange();
		
		/* Ако това е първата промяна ще върне 'null' и затова проверявам. */
		if(oldChange != null) {
			
			Entity newChange = new Entity("Change");
			newChange.setProperty("changeVersion", (Long)oldChange.getProperty("changeVersion")+1);
			newChange.setProperty("dayKey", dayKey);
			datastore.put(newChange);
			
		} else {
			
			Entity newChange = new Entity("Change");
			newChange.setProperty("changeVersion", 1);
			newChange.setProperty("dayKey", dayKey);
			datastore.put(newChange);
		}
	}
	
	public Entity getLastChange() {
		
		Entity lastChange = null;
		
		Query q = new Query("Change").addSort("changeVersion", SortDirection.DESCENDING);
		
		List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		
		try {	
			lastChange = pq.get(0);
		} catch (IndexOutOfBoundsException e) {}
		
		return lastChange;
	}
	
	public UpdateResponse getShiftList(long clientDBversion) {
		
		long dbLastVersion = 0;
		List<Shift> shiftList = new ArrayList<>();
		
		Query q = new Query("Change").setFilter(new FilterPredicate("changeVersion", FilterOperator.GREATER_THAN, clientDBversion));
		q.addSort("changeVersion", SortDirection.ASCENDING);
		
		List<Entity> changeList = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		if(!(changeList.isEmpty())) {
			
			// Това ще запише, коя е последната версия.
			dbLastVersion = (Long)changeList.get(changeList.size() -1).getProperty("changeVersion");
			
			ArrayList<Key> shiftsKeyList = new ArrayList<>();
			
			for(Entity entity: changeList) {
				
				shiftsKeyList.add((Key)entity.getProperty("dayKey"));
			}
			
			Map<Key, Entity> shiftEntityMap = datastore.get(shiftsKeyList);
			
			for(Map.Entry<Key, Entity> entry : shiftEntityMap.entrySet()) {
				
				Shift shift = new Shift();
				
				Long year = (Long)entry.getValue().getProperty("Year");
				shift.setYear(year.intValue());
				Long month = (Long)entry.getValue().getProperty("Month");
				shift.setMonth(month.intValue());
				Long day = (Long)entry.getValue().getProperty("Day");
				shift.setDay(Integer.valueOf(day.intValue()));
				shift.setPanMehanik((String)entry.getValue().getProperty("panMehanik"));
				shift.setPanKasaOne((String)entry.getValue().getProperty("panKasaOne"));
				shift.setPanKasaTwo((String)entry.getValue().getProperty("panKasaTwo"));
				shift.setPanKasaThree((String)entry.getValue().getProperty("panKasaThree"));
				shift.setRazporeditelOne((String)entry.getValue().getProperty("razporeditelOne"));
				shift.setRazporeditelTwo((String)entry.getValue().getProperty("razporeditelTwo"));
				shift.setCenMehanik("");
				shift.setCenKasa("");
				
				shiftList.add(shift);				
			}
		}
		
		return new UpdateResponse(dbLastVersion, shiftList);
	}

}
