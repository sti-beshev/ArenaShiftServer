package com.beshev.arenashiftserver.user;

import com.beshev.arenashiftserver.LoginInfo;
import com.beshev.arenashiftserver.ServerResponseMessage;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class AdminUserManager {
	
	public static final String ADMIN_KIND = "Admin";
	public static final String ADMIN_PASSWORD = "password";
	
	protected DatastoreService datastore;

	public AdminUserManager() {
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
	}
	
	public boolean checkAdminCredentiols(LoginInfo loginInfo) {
		
		Key adminKey = KeyFactory.createKey(ADMIN_KIND, loginInfo.getUsername());
		
		try {
			
			Entity adminEntity = datastore.get(adminKey);
			
			if (adminEntity.getProperty(ADMIN_PASSWORD).equals(loginInfo.getPassword())) return true;
					
		} catch (EntityNotFoundException e) {
			
			// This will create admin if there is none
			return createDefaultAdminIfNeeded(loginInfo.getUsername());
		}
		
		return false;
	}
	
	public ServerResponseMessage<String> changeAdminPassword(AdminChangePassInfo userInfo) 
			throws IllegalArgumentException {
		
		boolean haveError = false;
		String status = "";
		
		if(userInfo.getNewPassword().length() < 6) throw new IllegalArgumentException();
		
		Key adminKey = KeyFactory.createKey(ADMIN_KIND, userInfo.getUsername());
		
		try {
			
			Entity adminEntity = datastore.get(adminKey);
			
			if (adminEntity.getProperty(ADMIN_PASSWORD).equals(userInfo.getCurrentPassword())) {
				
				adminEntity.setProperty(ADMIN_PASSWORD, userInfo.getNewPassword());
				
				datastore.put(adminEntity);
				
				status = "Password is changed";
			
			} else {
				
				haveError = true;
				status = "Wrong password";
			}
			
			
		} catch (EntityNotFoundException e) {}
		
		return new ServerResponseMessage<>(status, haveError, null);
	}
	
	protected boolean createDefaultAdminIfNeeded(String username) {
		
		if (username.equals("admin")) {
			
			Entity adminEntity = new Entity(ADMIN_KIND, username);
			adminEntity.setProperty(ADMIN_PASSWORD, "adminadmin");
			
			datastore.put(adminEntity);
			
			return true;
		}
		
		return false;
	}

}
