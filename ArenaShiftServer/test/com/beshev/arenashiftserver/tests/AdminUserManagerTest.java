package com.beshev.arenashiftserver.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.beshev.arenashiftserver.LoginInfo;
import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.user.AdminUserManager;
import com.beshev.arenashiftserver.user.AdminChangePassInfo;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class AdminUserManagerTest {
	
	private final LocalServiceTestHelper helper;	
	private final DatastoreService datastore;
	private Entity adminEntity;
	
	private AdminUserManager adminUserManager;
	
	public AdminUserManagerTest() {
		
		helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		helper.setUp();
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
		adminEntity = new Entity(AdminUserManager.ADMIN_KIND, "admin");
		adminEntity.setProperty(AdminUserManager.ADMIN_PASSWORD, "adminadmin");
		
		adminUserManager = new AdminUserManager();
	}
	
	
	@Before
	  public void setUp() {
	    helper.setUp();
	    
	    datastore.put(adminEntity);
	  }

	  @After
	  public void tearDown() {
		  
		 datastore.delete(adminEntity.getKey());
	    
		 helper.tearDown();
	  }
	  
	  @Test
	  public void changeAdminPasswordTest() {
		  
		  AdminChangePassInfo userInfo = new AdminChangePassInfo("admin", "adminadmin", "newpass");
		  ServerResponseMessage<String> serverResponseMessage = adminUserManager.changeAdminPassword(userInfo);
		  
		  LoginInfo loginInfo = new LoginInfo("admin", "newpass");
		  boolean result = adminUserManager.checkAdminCredentiols(loginInfo);
		  
		  assertFalse(serverResponseMessage.isError());
		  assertTrue(result);
	  }
	  
	  @Test(expected = IllegalArgumentException.class)
	  public void changeAdminPasswordSmallPasswordTest() {
		  
		  AdminChangePassInfo userInfo = new AdminChangePassInfo("admin", "adminadmin", "pass");
		  adminUserManager.changeAdminPassword(userInfo);
	  }
	  
	  @Test
	  public void checkAdminCredentiolsCorrectCredentiols() {
		  
		  LoginInfo loginInfo = new LoginInfo("admin", "adminadmin");
		  boolean resultTrueLogin = adminUserManager.checkAdminCredentiols(loginInfo);
		  
		  assertTrue(resultTrueLogin);	  
	  }
	  
	  @Test
	  public void checkAdminCredentiolsWrongCredentiols() {
		  
		  LoginInfo loginInfo = new LoginInfo("admin", "wrong_password");
		  boolean resultFalseLogin = adminUserManager.checkAdminCredentiols(loginInfo);
		  
		  assertFalse(resultFalseLogin);	  
	  }
	  
	  @Test
	  public void checkAdminCredentiolsWrongUsername() {
		  
		  LoginInfo loginInfo = new LoginInfo("no_admin", "adminadmin");
		  boolean resultWrongUsernameLogin = adminUserManager.checkAdminCredentiols(loginInfo);
		  
		  assertFalse(resultWrongUsernameLogin);  
	  }

}
