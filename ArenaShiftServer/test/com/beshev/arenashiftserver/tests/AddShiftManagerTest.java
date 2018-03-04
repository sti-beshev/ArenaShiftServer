package com.beshev.arenashiftserver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		  String saveStatus = addShiftManager.saveShift(new Shift(2016, 7, 4, "Венци", "Гергана", "Жана", "няма", 
				  																										"Дафинка", "Бинка", "", ""));
		  
		  Shift shift = getShiftManager.getShift(new ShiftDate(2016, 7, 3));
		  
		  assertEquals("Смяната е запаметена", saveStatus);
		  assertEquals(2016, shift.getYear());
		  assertEquals(7, shift.getMonth());
		  assertEquals(3, shift.getDay());
		  assertEquals("Венци", shift.getPanMehanik());
		  assertEquals("Гергана", shift.getPanKasaOne());
		  assertEquals("Жана", shift.getPanKasaTwo());
		  assertEquals("няма", shift.getPanKasaThree());
		  assertEquals("Дафинка", shift.getRazporeditelOne());
		  assertEquals("Бинка", shift.getRazporeditelTwo());
		  
		  assertNotNull(getShiftManager.getShift(new ShiftDate(2016, 7, 4)));
	  }
	  
	  /* Да запамети смяна в ден, в който вече има смяна. */
	  @Test
	  public void testSaveShiftSecondTime() {
		  
		  String saveStatus = addShiftManager.saveShift(TestShift.getTestShift());
		  Shift shift = getShiftManager.getShift(new ShiftDate(2016, 7, 3));
		  
		  assertEquals("Смяна за този ден съществува вече", saveStatus);
		  assertEquals("Венци", shift.getPanMehanik());
		  
	  }
		  
}
