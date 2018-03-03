package com.beshev.arenashiftserver.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import com.beshev.arenashiftserver.event.ArenaShiftEventManager;
import com.beshev.arenashiftserver.event.ArenaShiftUserEvent;

@UserSecure
@Path("/event")
public class EventRestManager {
	
	@PUT
	@Path("/sync/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void syncEvents(@PathParam("username") String username, ArrayList<ArenaShiftUserEvent> eventList) {
		
		ArenaShiftEventManager eventManager = new ArenaShiftEventManager();
		DateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss.SSS", Locale.US);
		format.setTimeZone(TimeZone.getTimeZone("EET"));
		
		for(ArenaShiftUserEvent event: eventList) {
			
			Date date = null;
			
            try {
                date = format.parse(event.getDateAsString());
            } catch (ParseException e) {}
            
            eventManager.addEvent(username, date, event.getUserEvent());
		}
		
	}

}
