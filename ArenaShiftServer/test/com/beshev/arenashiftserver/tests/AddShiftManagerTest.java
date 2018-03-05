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
	  
	  @Before
	  public void setUp() {
		  
	    helper.setUp();
	    
		// Нова година, месец и ден.
		addShiftManager.saveShift(TestShift.getTestShift());
		
	  }

	  @After
	  public void tearDown() {
	    helper.tearDown();
	  }
	  
	  @Test
	  public void testSaveShift() {
		  
		  // Същестуваща година и месец но нов ден.
		  ServerResponseMessage<String> serverResponseMessage = addShiftManager.saveShift(new Shift(2016, 7, 4, "Венци", "Гергана", "Жана", "няма", 
				  																										"Дафинка", "Бинка", "", ""));
		  
		  ServerResponseMessage<Shift> serverResponseMessageWithShift = getShiftManager.getShift(new ShiftDate(2016, 7, 3));
		  Shift shift = serverResponseMessageWithShift.getPayload();
		  
		  assertFalse(serverResponseMessage.isError());
		  assertFalse(serverResponseMessageWithShift.isError());
		  
		  assertEquals("Смяната е запаметена", serverResponseMessage.getMessage());
		  assertEquals(2016, shift.getYear());
		  assertEquals(7, shift.getMonth());
		  assertEquals(3, shift.getDay());
		  assertEquals("Венци", shift.getPanMehanik());
		  assertEquals("Гергана", shift.getPanKasaOne());
		  assertEquals("Жана", shift.getPanKasaTwo());
		  assertEquals("няма", shift.getPanKasaThree());
		  assertEquals("Дафинка", shift.getRazporeditelOne());
		  assertEquals("Бинка", shift.getRazporeditelTwo());
		  
		  assertNotNull( getShiftManager.getShift( new ShiftDate( 2016, 7, 4 ) ).getPayload() );
	  }
	  
	  /* Да запамети смяна в ден, в който вече има смяна. */
	  @Test
	  public void testSaveShiftSecondTime() {
		  
		  ServerResponseMessage<String> serverResponseMessage = addShiftManager.saveShift(TestShift.getTestShift());
		  Shift shift = getShiftManager.getShift( new ShiftDate( 2016, 7, 3 ) ).getPayload();
		  
		  assertTrue(serverResponseMessage.isError());
		  assertEquals("Смяна за този ден съществува вече", serverResponseMessage.getMessage());
		  assertEquals("Венци", shift.getPanMehanik());
		  
	  }
		  
}
