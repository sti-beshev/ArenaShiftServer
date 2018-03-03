package com.beshev.arenashiftserver.user;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class ClientUserManager {
	
	private static final String USER_KIND = "User";
	private DatastoreService datastore;

	public ClientUserManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
	}
	
	public String addClientUser(String username) {
		
		String resultMessage = "";
		
		Key userEntityKey = KeyFactory.createKey(USER_KIND, username);
		
		try {
			
			@SuppressWarnings("unused")
			Entity userEntity = datastore.get(userEntityKey);
			
			resultMessage = username + " is taken !";
			
		} catch (EntityNotFoundException e) {
			
			Entity userEntity = new Entity("User", username);
			userEntity.setProperty("clientUsername", genarateRandomString(16, false));
			userEntity.setProperty("clientPassword", genarateRandomString(16, false));
			userEntity.setProperty("activationCode", genarateRandomString(6, true));
			
			datastore.put(userEntity);
			
			resultMessage = username + " is added";
			
		}
		
		return resultMessage;
	}
	
	public boolean checkUserCredentials(String username, String password) {
		
		Query q = new Query(USER_KIND)
			        .setFilter(new FilterPredicate("clientUsername", FilterOperator.EQUAL, username))
			        .setFilter(new FilterPredicate("clientPassword", FilterOperator.EQUAL, password));

			PreparedQuery pq = datastore.prepare(q);
			Entity result = pq.asSingleEntity();
		
		return (result != null);
	}
	
	public HashMap<String, String> activateUser(String activationCode) {
		
		HashMap<String, String> userInfoMap = null;
		
		Query q = new Query(USER_KIND)
		        .setFilter(new FilterPredicate("activationCode", FilterOperator.EQUAL, activationCode));

		PreparedQuery pq = datastore.prepare(q);
		Entity result = pq.asSingleEntity();
		
		if(result != null) {
			
			userInfoMap = new HashMap<>();
			userInfoMap.put("username", result.getKey().getName());
			userInfoMap.put("clientUsername", result.getProperty("clientUsername").toString());
			userInfoMap.put("clientPassword", result.getProperty("clientPassword").toString());
			
			// Resets the activation code
			result.setProperty("activationCode", genarateRandomString(6, true));
			
			datastore.put(result);
		}
		
		return userInfoMap;
	}
	
	private static String genarateRandomString(int lettersCount, boolean onlyNumbers) {
		
		final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	    final String lower = upper.toLowerCase(Locale.ROOT);

	    final String digits = "0123456789";

	    String alphanum = "";
	    
	    final Random random = ThreadLocalRandom.current();
	    
	    if (onlyNumbers) {
	    	
	    	alphanum = digits;
	    	
	    } else {
	    	
	    	alphanum = upper + lower + digits;
	    }

	    final char[] symbols = alphanum.toCharArray();

	    final char[] buf = new char[lettersCount];
	    
	    for(int i=0; i < buf.length; i++) {
	    	
	    	buf[i] = symbols[random.nextInt(symbols.length)];
	    }
		
		return new String(buf);
	}

}
