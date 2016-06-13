package com.beshev.arenashiftserver;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

public class ChangeManager {
	
	DatastoreService datastore;

	public ChangeManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
	}
	
public void addChange(Key dayKey) {
		
		Query q = new Query("Change").addSort("changeVersion", SortDirection.DESCENDING);
		
		List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));
		Entity oldChange = null;
		try {
			oldChange = pq.get(0);
		} catch (IndexOutOfBoundsException e) {}
		
		if(oldChange != null) {
			
			Entity newChange = new Entity("Change");
			newChange.setProperty("changeVersion", (Long)oldChange.getProperty("changeVersion")+1);
			newChange.setProperty("dayKey", dayKey);
			datastore.put(newChange);
			
		} else {
			
			Entity newChange = new Entity("Change");
			newChange.setProperty("changeVersion", 0);
			newChange.setProperty("dayKey", dayKey);
			datastore.put(newChange);
		}
	}

}
