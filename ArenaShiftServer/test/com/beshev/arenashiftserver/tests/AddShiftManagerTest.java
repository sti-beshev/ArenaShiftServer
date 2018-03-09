package com.beshev.arenashiftserver.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.shift.AddShiftManager;
import com.beshev.arenashiftserver.shift.GetShiftManager;
import com.beshev.arenashiftserver.shift.Shift;
import com.beshev.arenashiftserver.shift.ShiftDate;
import com.beshev.arenashiftserver.tests.util.TestShift;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class AddShiftManagerTest {

	  private final LocalServiceTestHelper helper = 
			  new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	  
	  private final AddShiftManager addShiftManager = new AddShiftManager();
	  private final GetShiftManager getShiftManager = new GetShiftManager();
	  private ServerResponseMessage<String> serverResponseMessage;
	  
	  @Before
	  public void setUp() {
		  
	    helper.setUp();
	    
		// This is just a normal shift to be used in tests
		serverResponseMessage = addShiftManager.saveShift(TestShift.getTestShift());
	  }

	  @After
	  public void tearDown() {
	    helper.tearDown();
	  }
	  
	  @Test
	  public void testSaveShift() {
		  
		  ServerResponseMessage<Shift> serverResponseMessageWithShift = getShiftManager.getShift(new ShiftDate(2016, 7, 3));
		  Shift shift = serverResponseMessageWithShift.getPayload();
		  
		  assertFalse(serverResponseMessage.isError());
		  
		  assertFalse(serverResponseMessageWithShift.isError());
		  
		  assertEquals(2016, shift.getYear());
		  assertEquals(7, shift.getMonth());
		  assertEquals(3, shift.getDay());
		  assertEquals("Венци", shift.getPanMehanik());
		  assertEquals("Гергана", shift.getPanKasaOne());
		  assertEquals("Жана", shift.getPanKasaTwo());
		  assertEquals("няма", shift.getPanKasaThree());
		  assertEquals("Дафинка", shift.getRazporeditelOne());
		  assertEquals("Бинка", shift.getRazporeditelTwo());
		  
		  assertNotNull( getShiftManager.getShift( new ShiftDate( 2016, 7, 3 ) ).getPayload() );
	  }
	  
	  // Trying to save shift in day that already have a shift saved
	  @Test
	  public void testSaveShiftSecondTime() {
		  
		  ServerResponseMessage<String> serverResponseMessage = addShiftManager.saveShift(TestShift.getTestShift());
		  Shift shift = getShiftManager.getShift( new ShiftDate( 2016, 7, 3 ) ).getPayload();
		  
		  assertTrue(serverResponseMessage.isError());
		  assertEquals("This day already have a shift", serverResponseMessage.getMessage());
		  assertEquals("Венци", shift.getPanMehanik());
		  
	  }
		  
}
