package com.beshev.arenashiftserver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.user.ClientUserManager;
import com.beshev.arenashiftserver.user.UserInfo;
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
		  String label = "visitor";
		  boolean isWorking = true;
		  
		  ServerResponseMessage<String> serverResponseMessage = clientUserManager.addClientUser(new UserInfo(username, label, isWorking));
		  
		  Entity userEntity = datastore.get(KeyFactory.createKey(ClientUserManager.USER_KIND, username));
		  
		  assertNotNull(userEntity);
		  assertEquals(label, userEntity.getProperty(ClientUserManager.USER_LABEL));
		  assertEquals(isWorking, userEntity.getProperty(ClientUserManager.USER_IS_WORKING));
		  assertNotNull(userEntity.getProperty(ClientUserManager.CLIENT_USERNAME));
		  assertNotNull(userEntity.getProperty(ClientUserManager.CLIENT_PASSWORD));
		  assertNotNull(userEntity.getProperty(ClientUserManager.USER_ACTIVATION_CODE));
		  assertFalse(serverResponseMessage.isError());
	  }
	  
	  @Test
	  public void addClientUserUsernameExistTest() {
		  
		  String username = "John";
		  String label = "visitor";
		  
		  clientUserManager.addClientUser(new UserInfo(username, label, true));
		  
		  // Trying to add the same username
		  ServerResponseMessage<String> serverResponseMessage = clientUserManager.addClientUser(new UserInfo(username, label, true));
		  
		  assertTrue(serverResponseMessage.isError());
		  assertEquals(username + " is taken !", serverResponseMessage.getMessage());
	  }
	  
	  @Test( expected = IllegalArgumentException.class)
	  public void addClientUserEmptyUsername() {
		  
		  clientUserManager.addClientUser(new UserInfo("", "visitor", true));
	  }
}
