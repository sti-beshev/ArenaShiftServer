package com.beshev.arenashiftserver.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.beshev.arenashiftserver.event.ArenaShiftEventLog;
import com.beshev.arenashiftserver.event.ArenaShiftEventManager;

@Path("/event")
public class EventRestManager {
	
	@PUT
	@Path("/sync")
	@Consumes(MediaType.APPLICATION_JSON)
	public void syncEvents(ArenaShiftEventLog eventLog) {
		
		new ArenaShiftEventManager().addEventLog(eventLog);
		
	}

}
