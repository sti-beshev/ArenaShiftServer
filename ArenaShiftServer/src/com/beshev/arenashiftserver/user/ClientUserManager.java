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
	public static final String USER_LABEL = "label";
	public static final String USER_IS_WORKING = "isWorking";
	
	private DatastoreService datastore;

	public ClientUserManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
	}
	
	public ServerResponseMessage<String> addClientUser(UserInfo userInfo) throws IllegalArgumentException {
		
		boolean haveError = false;
		
		if(userInfo.getUsername().equals("")) throw new IllegalArgumentException();
		
		String resultMessage = "";
		
		Key userEntityKey = KeyFactory.createKey(USER_KIND, userInfo.getUsername());
		
		try {
			
			@SuppressWarnings("unused")
			Entity userEntity = datastore.get(userEntityKey);
			
			haveError = true;
			resultMessage = userInfo.getUsername()+ " is taken !";
			
		} catch (EntityNotFoundException e) {
			
			Entity userEntity = new Entity(USER_KIND, userInfo.getUsername());
			userEntity.setProperty(CLIENT_USERNAME, genarateRandomString(16, false));
			userEntity.setProperty(CLIENT_PASSWORD, genarateRandomString(16, false));
			userEntity.setProperty(USER_ACTIVATION_CODE, genarateRandomString(6, true));
			userEntity.setProperty(USER_LABEL, userInfo.getLabel());
			userEntity.setProperty(USER_IS_WORKING, userInfo.isWorking());
			
			datastore.put(userEntity);
			
			resultMessage = userInfo.getUsername() + " is added as " + userInfo.getLabel();
			
		}
		
		return new ServerResponseMessage<>(resultMessage, haveError, null);
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
