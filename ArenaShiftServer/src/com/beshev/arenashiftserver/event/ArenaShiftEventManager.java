package com.beshev.arenashiftserver.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class ArenaShiftEventManager {
	
	private DatastoreService datastore;

	public ArenaShiftEventManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
	}
	
	public void addEventLog(ArenaShiftEventLog eventLog) {
		
		Key userNameKey = createUserIfNeeded(eventLog.getUserName());
		
		List<Entity> entityList = new ArrayList<Entity>();
		
		for(ArenaShiftEvent event : eventLog.getEventsList()) {
			
			Entity eventEntity = new Entity("ArenaShiftEvent", userNameKey);
			eventEntity.setProperty("Date", event.getDate());
			eventEntity.setProperty("Event", event.getEvent());
			
			entityList.add(eventEntity);
			//datastore.put(eventEntity);
		}
		
		datastore.put(entityList);
	}
	
	public void addEvent(String userName, Date date, String event) {
		
		Key userNameKey = createUserIfNeeded(userName);
		
		Entity eventEntity = new Entity("ArenaShiftEvent", userNameKey);
		eventEntity.setProperty("Date", date);
		eventEntity.setProperty("Event", event);
		
		datastore.put(eventEntity);
	}
	
	private Key createUserIfNeeded(String userName) {
		
		Key userNameKey = KeyFactory.createKey("Username", userName);
		
		try {
			datastore.get(userNameKey);
		} catch (EntityNotFoundException e) {
			Entity userNameEntity = new Entity("Username", userName);
			datastore.put(userNameEntity);
		}
		
		return userNameKey;
	}

}
