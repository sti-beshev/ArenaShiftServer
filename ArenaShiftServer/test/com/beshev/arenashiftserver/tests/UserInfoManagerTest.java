package com.beshev.arenashiftserver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.user.ClientUserManager;
import com.beshev.arenashiftserver.user.UserInfoManager;
import com.beshev.arenashiftserver.user.WorkerInfo;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class UserInfoManagerTest {
	
	private final LocalServiceTestHelper helper;
	
	private UserInfoManager userInfoManager;
	private ClientUserManager clientUserManager;

	public UserInfoManagerTest() {
		
		helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		
		userInfoManager = new UserInfoManager();
		clientUserManager = new ClientUserManager();
		
	}
	
	@Before
	 public void setUp() {
	    helper.setUp();
	    
	    clientUserManager.addClientUser(new WorkerInfo("Bobo", "Usher", true));
	    clientUserManager.addClientUser(new WorkerInfo("Jonh", "Usher", true));
	    clientUserManager.addClientUser(new WorkerInfo("Kimbo", "Usher", false));
	    
	  }

	@After
	public void tearDown() {
	    helper.tearDown();
	  }
	
	@Test
	public void getUserInfoTest() {
		// No need for now.
	}

	
	@Test
	public void getAllWorkersInfoTest() {
		
		ServerResponseMessage<List<WorkerInfo>> serverResponseMessage = userInfoManager.getAllWorkersInfo();
		
		List<WorkerInfo> workersList = serverResponseMessage.getPayload();
		
		assertEquals(3, workersList.size());
		
		assertEquals("Jonh", workersList.get(1).getUsername());
		assertEquals("Usher", workersList.get(1).getLabel());
		assertTrue(workersList.get(1).isWorking());
	}
	
	@Test
	public void changeWorkerInfo() {
		
		ServerResponseMessage<String> serverResponseMessage = userInfoManager.changeWorkerInfo(new WorkerInfo("Kimbo", "Not usher", true));
		
		WorkerInfo workerInfo = null;
		
		try {
			workerInfo = userInfoManager.getWorkerInfo("Kimbo");
		} catch (EntityNotFoundException e) {}
		
		assertFalse(serverResponseMessage.isError());
		
		assertEquals("Not usher", workerInfo.getLabel());
		assertTrue(workerInfo.isWorking());
	}
	
	@Test
	public void getWorkerInfo() {
		// No need for now.
	}

}
