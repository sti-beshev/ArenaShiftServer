package com.beshev.arenashiftserver.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.beshev.arenashiftserver.shift.AddShiftManager;
import com.beshev.arenashiftserver.shift.Shift;
import com.beshev.arenashiftserver.tests.util.TestShift;
import com.beshev.arenashiftserver.update.ChangeManager;
import com.beshev.arenashiftserver.update.UpdateResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
	public void addChangeTest() throws EntityNotFoundException {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Shift shift = TestShift.getTestShift();
		
        Key monthKey = new KeyFactory.Builder("Year", shift.getYear()).addChild("Month", shift.getMonth()).getKey();
		
		Entity dayEntity = new Entity("Day", shift.getDay(), monthKey);
		dayEntity.setProperty("Year", shift.getYear());
		dayEntity.setProperty("Month", shift.getMonth());
		dayEntity.setProperty("Day", shift.getDay());
		dayEntity.setProperty("panMehanik", shift.getPanMehanik());
		dayEntity.setProperty("panKasaOne", shift.getPanKasaOne());
		dayEntity.setProperty("panKasaTwo", shift.getPanKasaTwo());
		dayEntity.setProperty("panKasaThree", shift.getPanKasaThree());
		dayEntity.setProperty("razporeditelOne", shift.getRazporeditelOne());
		dayEntity.setProperty("razporeditelTwo", shift.getRazporeditelTwo());
		datastore.put(dayEntity);
		
		Key dayKey = new KeyFactory.Builder("Year", shift.getYear()).addChild("Month", shift.getMonth()).addChild("Day", shift.getDay()).getKey();
		
		changeManager.addChange(dayKey);
		
		Entity lastChange = changeManager.getLastChange();
		Entity testDayEntity = datastore.get((Key)lastChange.getProperty("dayKey"));
		
		assertEquals((long)shift.getYear(), testDayEntity.getProperty("Year"));
		assertEquals((long)shift.getMonth(), testDayEntity.getProperty("Month"));
		assertEquals((long)shift.getDay(), testDayEntity.getProperty("Day"));

	}

	@Test
	public void getLastChangeTest() throws EntityNotFoundException {

		Long lastChangeNumber = (Long)changeManager.getLastChange().getProperty("changeVersion");
		Key lastChangeKey = (Key)changeManager.getLastChange().getProperty("dayKey");

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity shiftEntity;
		Shift shift = new Shift();

		shiftEntity = datastore.get(lastChangeKey);

		Long year = (Long)shiftEntity.getProperty("Year");
		shift.setYear(year.intValue());
		Long month = (Long)shiftEntity.getProperty("Month");
		shift.setMonth(month.intValue());
		Long day = (Long)shiftEntity.getProperty("Day");
		shift.setDay(day.intValue());
		shift.setPanMehanik((String)shiftEntity.getProperty("panMehanik"));
		shift.setPanKasaOne((String)shiftEntity.getProperty("panKasaOne"));

		assertEquals(3, lastChangeNumber.longValue());
		assertEquals(2016, shift.getYear());
		assertEquals(1, shift.getMonth());
		assertEquals(3, shift.getDay());
		assertEquals("Александър", shift.getPanMehanik());
		assertEquals("Наталия", shift.getPanKasaOne());
	} 

	@Test
	public void getShiftListTest() {

		UpdateResponse updateResponse = changeManager.getShiftList(0);

		List<Shift> shiftList = updateResponse.getChangesList();
		Shift shiftOne = shiftList.get(0);
		Shift shiftTwo = shiftList.get(1);
		Shift shiftThree = shiftList.get(2);

		assertEquals(3, shiftList.size());
		assertEquals(3, updateResponse.getDbVersion());
		assertEquals("Венци", shiftOne.getPanMehanik());
		assertEquals("Цвети", shiftOne.getPanKasaOne());
		assertEquals("Венци", shiftTwo.getPanMehanik());
		assertEquals("Елица", shiftTwo.getPanKasaOne());
		assertEquals("Александър", shiftThree.getPanMehanik());
		assertEquals("Наталия", shiftThree.getPanKasaOne());

	}

	@Test
	public void getShiftListNoChangeTest() {

		UpdateResponse updateResponse = changeManager.getShiftList(3);

		List<Shift> shiftList = updateResponse.getChangesList();

		assertTrue(shiftList.isEmpty());	
		assertEquals(0, updateResponse.getDbVersion());
	}

	private void setUpChanges(ChangeManager changeManager) {

		AddShiftManager addShiftManager = new AddShiftManager();

		addShiftManager.saveShift(new Shift(2016, 1, 1, "Венци", "Цвети", "Гергана", "няма", 
				"Дафинка", "Наталия", "", ""));
		addShiftManager.saveShift(new Shift(2016, 1, 2, "Венци", "Елица", "Цвети", "няма", 
				"Бинка", "Дафинка", "", ""));
		addShiftManager.saveShift(new Shift(2016, 1, 3, "Александър", "Наталия", "Жана", "няма", 
				"Цеца", "Бинка", "", ""));
	}
}
