package com.beshev.arenashiftserver.user;

import com.beshev.arenashiftserver.LoginInfo;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class AdminUserManager {
	
	public static final String ADMIN_KIND = "Admin";
	public static final String ADMIN_PASSWORD = "password";
	
	private DatastoreService datastore;

	public AdminUserManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
	}
	
	public boolean checkAdminCredentiols(LoginInfo loginInfo) {
		
		Key adminKey = KeyFactory.createKey(ADMIN_KIND, loginInfo.getUsername());
		
		try {
			
			Entity adminEntity = datastore.get(adminKey);
			
			if (adminEntity.getProperty(ADMIN_PASSWORD).equals(loginInfo.getPassword())) return true;
					
		} catch (EntityNotFoundException e) {}

		
		return false;
	}
	
	public String changeAdminPassword(UserChangePassInfo userInfo) throws IllegalArgumentException {
		
		if(userInfo.getNewPassword().length() < 6) throw new IllegalArgumentException();
		
		Key adminKey = KeyFactory.createKey(ADMIN_KIND, userInfo.getUsername());
		
		try {
			
			Entity adminEntity = datastore.get(adminKey);
			
			if (adminEntity.getProperty(ADMIN_PASSWORD).equals(userInfo.getCurrentPassword())) {
				
				adminEntity.setProperty(ADMIN_PASSWORD, userInfo.getNewPassword());
				
				datastore.put(adminEntity);
			
			} else {
				
				return "Грешна парола";
			}
			
			
		} catch (EntityNotFoundException e) {
			
			// Ако няма админ създай го.
			
			if (userInfo.getUsername().equals("admin")) {
				
				Entity adminEntity = new Entity(ADMIN_KIND, userInfo.getUsername());
				adminEntity.setProperty(ADMIN_PASSWORD, "adminadmin");
				
				datastore.put(adminEntity);
			}
		}
		
		return "Password changed";
	}

}
