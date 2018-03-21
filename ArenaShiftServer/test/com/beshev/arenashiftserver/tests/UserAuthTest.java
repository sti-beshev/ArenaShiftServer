package com.beshev.arenashiftserver.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beshev.arenashiftserver.user.ClientUserInfo;
import com.beshev.arenashiftserver.user.ClientUserManager;
import com.beshev.arenashiftserver.user.UserAuth;
import com.beshev.arenashiftserver.user.UserInfo;
import com.beshev.arenashiftserver.user.UserInfoManager;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class UserAuthTest {

	private final LocalServiceTestHelper helper;

	private ClientUserManager clientUserManager;
	private UserInfoManager userInfoManager;

	public UserAuthTest() {

		helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		clientUserManager = new ClientUserManager();
		userInfoManager = new UserInfoManager();
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
	public void checkUserCredentialsRightCredentials() throws EntityNotFoundException {

		String username = "John";
		String label = "visitor";

		clientUserManager.addClientUser(new UserInfo(username, label, false));

		ClientUserInfo clientUserInfo  = userInfoManager.getUserInfo(username);

		boolean result = UserAuth.checkUserCredentials(clientUserInfo.getClientUsername(), 
				clientUserInfo.getClientPassword());

		assertTrue(result);

	}

	@Test
	public void checkUserCredentialsWrongCredentials() throws EntityNotFoundException {

		String username = "John";
		String label = "visitor";

		clientUserManager.addClientUser(new UserInfo(username, label, false));

		ClientUserInfo clientUserInfo  = userInfoManager.getUserInfo(username);

		boolean result = UserAuth.checkUserCredentials(clientUserInfo.getClientUsername(), 
				"xcVbG56AaS8Kjl");

		assertFalse(result);

	}

}
