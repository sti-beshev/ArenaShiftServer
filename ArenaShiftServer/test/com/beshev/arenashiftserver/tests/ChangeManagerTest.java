package com.beshev.arenashiftserver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beshev.arenashiftserver.AddShiftManager;
import com.beshev.arenashiftserver.ChangeManager;
import com.beshev.arenashiftserver.Shift;
import com.beshev.arenashiftserver.UpdateResponse;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ChangeManagerTest {
	

	  private final LocalServiceTestHelper helper = 
			  new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	  
	  private final ChangeManager changeManager = new ChangeManager(); 
	  
	 

		  @Before
		  public void setUp() {
		    helper.setUp();
		    setUpChanges(changeManager);
		  }

		  @After
		  public void tearDown() {
		    helper.tearDown();
		  }
		  
		  @Test
		  public void getShiftListTest() {
			  
			 UpdateResponse updateResponse = changeManager.getShiftList(0);
			  
			 List<Shift> shiftList = updateResponse.getChangesList();
			 Shift shiftOne = shiftList.get(0);
			 Shift shiftTwo = shiftList.get(1);
			 
			 assertEquals(3, shiftList.size());
			 assertEquals(3, updateResponse.getDbVersion());
			 assertEquals("Венци", shiftOne.getPanMehanik());
			 assertEquals("Цвети", shiftOne.getPanKasaOne());
			 assertEquals("Венци", shiftTwo.getPanMehanik());
			 assertEquals("Елица", shiftTwo.getPanKasaOne());
			 
		  }
		  
		  @Test
		  public void getShiftListNoChangeTest() {
			  
			 UpdateResponse updateResponse = changeManager.getShiftList(3);
			  
			 List<Shift> shiftList = updateResponse.getChangesList();
			 
			 assertTrue(shiftList.isEmpty());	
			 assertEquals(0, updateResponse.getDbVersion());		// Нула защото няма промяна и не е нужна версия.
		
			 
		  }
		  
		  private void setUpChanges(ChangeManager changeManager) {
			  
			  AddShiftManager addShiftManager = new AddShiftManager();
			  
			  addShiftManager.saveShift(new Shift(2016, 1, 1, "Венци", "Цвети", "Гергана", "няма", 
					  "Дафинка", "Наталия", "Иван", "Катя"));
			  addShiftManager.saveShift(new Shift(2016, 1, 2, "Венци", "Елица", "Цвети", "няма", 
					  "Бинка", "Дафинка", "Иван", "Жана"));
			  addShiftManager.saveShift(new Shift(2016, 1, 3, "Александър", "Наталия", "Жана", "няма", 
					  "Цеца", "Бинка", "Александър", "Гергана"));
		  }

}
