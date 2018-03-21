package com.beshev.arenashiftserver.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.user.ClientUserManager;
import com.beshev.arenashiftserver.user.UserInfo;
import com.beshev.arenashiftserver.user.UserLabelManager;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import static org.junit.Assert.*;

public class UserLabelManagerTest {

	private final LocalServiceTestHelper helper;
	
	private UserLabelManager userLabelManager;
	
	public UserLabelManagerTest() {
		
		helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		
		userLabelManager = new UserLabelManager();
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
	public void getUsernamesOfLabelTest() {
		
		ClientUserManager clientUserManager = new ClientUserManager();
		
		clientUserManager.addClientUser(new UserInfo("Jonh", "worker", true));
		clientUserManager.addClientUser(new UserInfo("Math", "worker", true));
		clientUserManager.addClientUser(new UserInfo("Joy", "messanger", true));
		clientUserManager.addClientUser(new UserInfo("Vim", "worker", false));
		
		ServerResponseMessage<List<String>> serverResponseMessage = userLabelManager.getUsernamesOfLabel("worker");

		ArrayList<String> userNames = new ArrayList<>(serverResponseMessage.getPayload());
		
		assertEquals(3, userNames.size());
		assertTrue(userNames.contains("Jonh"));
		assertTrue(userNames.contains("Math"));
		assertTrue(userNames.contains("Vim"));
	}

}
