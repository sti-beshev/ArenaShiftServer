package com.beshev.arenashiftserver.tests.util;

import com.beshev.arenashiftserver.shift.Shift;

public class TestShift {
	
	private static Shift testShift = new Shift(2016, 7, 3, "Венци", "Гергана", "Жана", "няма", "Дафинка", "Бинка", "", "");
	
	public static Shift getTestShift() {
		return testShift;
	}

}
