package com.beshev.arenashiftserver.user;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UserAuth {

public static boolean checkUserCredentials(String username, String password) {
	
	 DatastoreService datastore =  DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query(ClientUserManager.USER_KIND)
			        .setFilter(new FilterPredicate(ClientUserManager.CLIENT_USERNAME, FilterOperator.EQUAL, username))
			        .setFilter(new FilterPredicate(ClientUserManager.CLIENT_PASSWORD, FilterOperator.EQUAL, password));

		PreparedQuery pq = datastore.prepare(q);
		Entity result = pq.asSingleEntity();
		
		return (result != null);
	}

}
