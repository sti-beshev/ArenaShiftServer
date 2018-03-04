package com.beshev.arenashiftserver.user;

import com.beshev.arenashiftserver.LoginInfo;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class AdminUserManager {
	
	DatastoreService datastore;

	public AdminUserManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
	}
	
	public boolean checkAdminCredentiols(LoginInfo loginInfo) {
		
		Key adminKey = KeyFactory.createKey("Admin", loginInfo.getUsername());
		
		try {
			
			Entity adminEntity = datastore.get(adminKey);
			
			if (adminEntity.getProperty("password").equals(loginInfo.getPassword())) return true;
					
		} catch (EntityNotFoundException e) {}

		
		return false;
	}
	
	public String changeAdminPassword(UserChangePassInfo userInfo) throws IllegalArgumentException {
		
		if(userInfo.getNewPassword().length() < 6) {
			
			throw new IllegalArgumentException();
		}
		
		Key adminKey = KeyFactory.createKey("Admin", userInfo.getUsername());
		
		try {
			
			Entity adminEntity = datastore.get(adminKey);
			
			if (adminEntity.getProperty("password").equals(userInfo.getCurrentPassword())) {
				
				adminEntity.setProperty("password", userInfo.getNewPassword());
				
				datastore.put(adminEntity);
			
			} else {
				
				return "Грешна парола";
			}
			
			
		} catch (EntityNotFoundException e) {
			
			// Ако няма админ създай го.
			
			if (userInfo.getUsername().equals("admin")) {
				
				Entity adminEntity = new Entity("Admin", userInfo.getUsername());
				adminEntity.setProperty("password", "adminadmin");
				
				datastore.put(adminEntity);
			}
		}
		
		return "Password changed";
	}

}
