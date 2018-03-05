package com.beshev.arenashiftserver.user;

import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.beshev.arenashiftserver.ServerResponseMessage;
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
	
	public static final String USER_KIND = "User";
	public static final String USER_USERNAME = "username";
	public static final String CLIENT_USERNAME = "clientUsername";
	public static final String CLIENT_PASSWORD = "clientPassword";
	public static final String USER_ACTIVATION_CODE = "activationCode";
	
	private DatastoreService datastore;

	public ClientUserManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
	}
	
	public ServerResponseMessage<String> addClientUser(String username) throws IllegalArgumentException {
		
		boolean haveError = false;
		
		if(username.equals("")) throw new IllegalArgumentException();
		
		String resultMessage = "";
		
		Key userEntityKey = KeyFactory.createKey(USER_KIND, username);
		
		try {
			
			@SuppressWarnings("unused")
			Entity userEntity = datastore.get(userEntityKey);
			
			haveError = true;
			resultMessage = username + " is taken !";
			
		} catch (EntityNotFoundException e) {
			
			Entity userEntity = new Entity(USER_KIND, username);
			userEntity.setProperty(CLIENT_USERNAME, genarateRandomString(16, false));
			userEntity.setProperty(CLIENT_PASSWORD, genarateRandomString(16, false));
			userEntity.setProperty(USER_ACTIVATION_CODE, genarateRandomString(6, true));
			
			datastore.put(userEntity);
			
			resultMessage = username + " is added";
			
		}
		
		return new ServerResponseMessage<>(resultMessage, haveError, null);
	}
	
	public boolean checkUserCredentials(String username, String password) {
		
		Query q = new Query(USER_KIND)
			        .setFilter(new FilterPredicate(CLIENT_USERNAME, FilterOperator.EQUAL, username))
			        .setFilter(new FilterPredicate(CLIENT_PASSWORD, FilterOperator.EQUAL, password));

			PreparedQuery pq = datastore.prepare(q);
			Entity result = pq.asSingleEntity();
		
		return (result != null);
	}
	
	public HashMap<String, String> activateUser(String activationCode) {
		
		HashMap<String, String> userInfoMap = null;
		
		Query q = new Query(USER_KIND)
		        .setFilter(new FilterPredicate(USER_ACTIVATION_CODE, FilterOperator.EQUAL, activationCode));

		PreparedQuery pq = datastore.prepare(q);
		Entity result = pq.asSingleEntity();
		
		if(result != null) {
			
			userInfoMap = new HashMap<>();
			userInfoMap.put(USER_USERNAME, result.getKey().getName());
			userInfoMap.put(CLIENT_USERNAME, result.getProperty(CLIENT_USERNAME).toString());
			userInfoMap.put(CLIENT_PASSWORD, result.getProperty(CLIENT_PASSWORD).toString());
			
			// Resets the activation code
			result.setProperty(USER_ACTIVATION_CODE, genarateRandomString(6, true));
			
			datastore.put(result);
		}
		
		return userInfoMap;
	}
	
	public ClientUserInfo getUserInfo(String username) throws EntityNotFoundException {
		
		ClientUserInfo clientUserInfo = null;
		
		Entity userEntity = datastore.get(KeyFactory.createKey(USER_KIND, username));
		
		clientUserInfo = new ClientUserInfo(userEntity.getKey().getName(),
				userEntity.getProperty(CLIENT_USERNAME).toString(),
				userEntity.getProperty(CLIENT_PASSWORD).toString(),
				userEntity.getProperty(USER_ACTIVATION_CODE).toString());
		
		return clientUserInfo;
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
