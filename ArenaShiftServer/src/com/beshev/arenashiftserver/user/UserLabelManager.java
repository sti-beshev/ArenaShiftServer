package com.beshev.arenashiftserver.user;

import java.util.ArrayList;
import java.util.List;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UserLabelManager {
	
	private DatastoreService datastore;

	public UserLabelManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();	
	}
	
	public ServerResponseMessage<List<String>> getUsernamesOfLabel(String userLabel) {
		
		List<String> userNames;
		
		Query q = new Query(ClientUserManager.USER_KIND)
		        .setFilter(new FilterPredicate(ClientUserManager.USER_LABEL, FilterOperator.EQUAL, userLabel))
		        .setKeysOnly();

		PreparedQuery pq = datastore.prepare(q);
		List<Entity> usersKeysList = pq.asList(FetchOptions.Builder.withDefaults());
		
		if (usersKeysList.isEmpty()) {
			
			return new ServerResponseMessage<List<String>>("No users with label : " + userLabel, true, null);
			
		} else {
			
			userNames = new ArrayList<>();
			
			for(Entity entity : usersKeysList) {
				
				userNames.add(entity.getKey().getName());
			}
		}
		
		return new ServerResponseMessage<List<String>>("", false, userNames);
	}

}
