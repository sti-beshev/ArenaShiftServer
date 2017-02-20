package com.beshev.arenashiftserver.tests
;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beshev.arenashiftserver.event.ArenaShiftEvent;
import com.beshev.arenashiftserver.event.ArenaShiftEventLog;
import com.beshev.arenashiftserver.event.ArenaShiftEventManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ArenaShiftEventManagerTest {

	 private final LocalServiceTestHelper helper = 
			  new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	 
	 private final ArenaShiftEventManager eventManager = new ArenaShiftEventManager();
	 private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	 
	 @Before
	  public void setUp() {
		  
	    helper.setUp();
	  }

	  @After
	  public void tearDown() {
	    helper.tearDown();
	  }
	  
	  @Test
	  public void addEventLogTest() {
		  
		  final String userName = "Венци";
		  
		  ArrayList<ArenaShiftEvent> eventsList = new ArrayList<ArenaShiftEvent>();
		  Date dateOne = new Date();
		  eventsList.add(new ArenaShiftEvent(dateOne, "Hello"));
		  Date dateTwo = new Date();
		  eventsList.add(new ArenaShiftEvent(dateTwo, "Hello2"));
		  Date dateThree = new Date();
		  eventsList.add(new ArenaShiftEvent(dateThree, "Hello3"));
		  
		  ArenaShiftEventLog eventLog = new ArenaShiftEventLog();
		  eventLog.setUserName(userName);
		  eventLog.setEventsList(eventsList);
		  
		  eventManager.addEventLog(eventLog);
		  
		  Query q = new Query("ArenaShiftEvent").setAncestor(KeyFactory.createKey("Username", userName));
		  q.addSort("Date", SortDirection.ASCENDING);
			
		  List<Entity> eventResultList = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		  
		  ArrayList<Entity> eventResultArrayList = new ArrayList<Entity>();
		  for(Entity entity : eventResultList) {
			  eventResultArrayList.add(entity);
		  }
		  
		  assertEquals(dateOne, (Date)eventResultArrayList.get(0).getProperty("Date"));
		  assertEquals("Hello", (String)eventResultArrayList.get(0).getProperty("Event"));
		  
		  assertEquals(dateThree, (Date)eventResultArrayList.get(2).getProperty("Date"));
		  assertEquals("Hello3", (String)eventResultArrayList.get(2).getProperty("Event"));
	  }
	  
	  @Test
	  public void addEventTest() {
		  
		  final String userName = "Багряна";
		  final Date date = new Date();
		  
		  eventManager.addEvent(userName, date, "Noob");
		  
		  Query q = new Query("ArenaShiftEvent").setAncestor(KeyFactory.createKey("Username", userName));
			
		  List<Entity> eventResultList = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		  
		  assertEquals(date, (Date)eventResultList.get(0).getProperty("Date"));
		  assertEquals("Noob", (String)eventResultList.get(0).getProperty("Event"));
	  }

}
