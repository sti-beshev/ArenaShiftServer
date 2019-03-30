package com.beshev.arenashiftserver.user;

import org.mindrot.jbcrypt.BCrypt;

import com.beshev.arenashiftserver.LoginInfo;
import com.beshev.arenashiftserver.ServerResponseMessage;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class AdminUserManagerCrypto extends AdminUserManager {
	
	private static final int NUMBER_OF_ROUNDS = 4;  // 4 is the smallest possible

	public AdminUserManagerCrypto() {
		super();
		
	}
	
	@Override
	public boolean checkAdminCredentiols(LoginInfo loginInfo) {
		
		Key adminKey = KeyFactory.createKey(ADMIN_KIND, loginInfo.getUsername());
		
		try {
			
			String adminPassword = datastore.get(adminKey)
																.getProperty(ADMIN_PASSWORD)
																.toString();
			
			if (BCrypt.checkpw(loginInfo.getPassword(), adminPassword)) return true;
					
		} catch (EntityNotFoundException e) {
			
			// This will create admin if there is none
			return createDefaultAdminIfNeeded(loginInfo.getUsername());
		}
		
		return false;
	}
	
	@Override
	public ServerResponseMessage<String> changeAdminPassword(AdminChangePassInfo userInfo) 
			throws IllegalArgumentException {
		
		boolean haveError = false;
		String status = "";
		
		if(userInfo.getNewPassword().length() < 6) throw new IllegalArgumentException();
		
		Key adminKey = KeyFactory.createKey(ADMIN_KIND, userInfo.getUsername());
		
		try {
			
			Entity adminEntity = datastore.get(adminKey);
			
			if (checkAdminCredentiols(new LoginInfo(userInfo.getUsername(), userInfo.getCurrentPassword()))) {
				
				adminEntity.setProperty(ADMIN_PASSWORD, 
						BCrypt.hashpw(userInfo.getNewPassword(), BCrypt.gensalt(NUMBER_OF_ROUNDS)));
				
				datastore.put(adminEntity);
				
				status = "Password is changed";
			
			} else {
				
				haveError = true;
				status = "Wrong password";
			}
			
			
		} catch (EntityNotFoundException e) {}
		
		return new ServerResponseMessage<>(status, haveError, null);
	}
	
	@Override
	protected boolean createDefaultAdminIfNeeded(String username) {
		
		if (username.equals("admin")) {
			
			Entity adminEntity = new Entity(ADMIN_KIND, username);
			adminEntity.setProperty(ADMIN_PASSWORD, BCrypt.hashpw("adminadmin", BCrypt.gensalt(NUMBER_OF_ROUNDS)));
			
			datastore.put(adminEntity);
			
			return true;
		}
		
		return false;
	}

}
