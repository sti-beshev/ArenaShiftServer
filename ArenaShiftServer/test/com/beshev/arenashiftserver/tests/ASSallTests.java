package com.beshev.arenashiftserver.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AddShiftManagerTest.class, 
									 ChangeManagerTest.class,
									 ArenaShiftEventManagerTest.class,
									 AdminUserManagerTest.class,
									 ClientUserManagerTest.class,
									 UserLabelManagerTest.class,
									 UserAuthTest.class})
public class ASSallTests {
}
