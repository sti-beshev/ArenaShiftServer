package com.beshev.arenashiftserver.event;

import java.util.ArrayList;

public class ArenaShiftEventLog {
	
	private String userName;
	private ArrayList<ArenaShiftEvent> eventsList = new ArrayList<ArenaShiftEvent>();

	public ArenaShiftEventLog() {
		
	}

	public ArenaShiftEventLog(String userName, ArrayList<ArenaShiftEvent> eventsList) {
		super();
		this.userName = userName;
		this.eventsList = eventsList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<ArenaShiftEvent> getEventsList() {
		return eventsList;
	}

	public void setEventsList(ArrayList<ArenaShiftEvent> eventsList) {
		this.eventsList = eventsList;
	}

}
