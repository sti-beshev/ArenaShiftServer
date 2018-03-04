package com.beshev.arenashiftserver.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.beshev.arenashiftserver.user.ClientUserInfo;
import com.beshev.arenashiftserver.user.ClientUserManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ClientUserManagerTest {

	private final LocalServiceTestHelper helper;
	private final DatastoreService datastore;
	
	private ClientUserManager clientUserManager;
	
	public ClientUserManagerTest() {
		
		helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		helper.setUp();
		
		datastore = DatastoreServiceFactory.getDatastoreService();
		
		clientUserManager = new ClientUserManager();
	}
	
	@Before
	  public void setUp() {
	    helper.setUp();
	    
	  }

	  @After
	  public void tearDown() {
	    helper.tearDown();
	  }
	  
	  @Test
	  public void addClientUserTest() throws EntityNotFoundException {
		  
		  String username = "John";
		  
		  clientUserManager.addClientUser(username);
		  
		  Entity userEntity = datastore.get(KeyFactory.createKey(ClientUserManager.USER_KIND, username));
		  
		  assertNotNull(userEntity);
		  assertNotNull(userEntity.getProperty(ClientUserManager.CLIENT_USERNAME));
		  assertNotNull(userEntity.getProperty(ClientUserManager.CLIENT_PASSWORD));
		  assertNotNull(userEntity.getProperty(ClientUserManager.USER_ACTIVATION_CODE));
	  }
	  
	  @Test
	  public void addClientUserUsernameExistTest() {
		  
		  String username = "John";
		  
		  clientUserManager.addClientUser(username);
		  
		  // Trying to add the same username
		  String resultMSG = clientUserManager.addClientUser(username);
		  
		  assertEquals(username + " is taken !", resultMSG);
	  }
	  
	  @Test( expected = IllegalArgumentException.class)
	  public void addClientUserEmptyUsername() {
		  
		  clientUserManager.addClientUser("");
	  }
	  
	  @Test
	  public void checkUserCredentialsRightCredentials() throws EntityNotFoundException {
		  
		  String username = "John";
		  
		  clientUserManager.addClientUser(username);
			  
		  ClientUserInfo clientUserInfo  = clientUserManager.getUserInfo(username);
			  
		  boolean result = clientUserManager.checkUserCredentials(clientUserInfo.getClientUsername(), 
					  clientUserInfo.getClientPassword());
		  
		  assertTrue(result);

	  }
	  
	  @Test
	  public void checkUserCredentialsWrongCredentials() throws EntityNotFoundException {
		  
		  String username = "John";
		  
		  clientUserManager.addClientUser(username);
			  
		  ClientUserInfo clientUserInfo  = clientUserManager.getUserInfo(username);
			  
		  boolean result = clientUserManager.checkUserCredentials(clientUserInfo.getClientUsername(), 
					  "xcVbG56AaS8Kjl");
		  
		  assertFalse(result);

	  }

}
