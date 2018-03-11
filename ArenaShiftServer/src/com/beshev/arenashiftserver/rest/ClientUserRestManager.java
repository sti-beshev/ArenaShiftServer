package com.beshev.arenashiftserver.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.beshev.arenashiftserver.event.ArenaShiftEventManager;
import com.beshev.arenashiftserver.event.ArenaShiftUserEvent;
import com.beshev.arenashiftserver.update.UpdateClientManager;
import com.beshev.arenashiftserver.update.UpdateRequest;
import com.beshev.arenashiftserver.update.UpdateResponse;
import com.beshev.arenashiftserver.user.ClientUserManager;

@Path("/user")
public class ClientUserRestManager {

	@GET
	@Path("/activate/{activationCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> activateUser(@PathParam("activationCode") String activationCode) {
		
		HashMap<String, String> userInfoMap = new ClientUserManager().activateUser(activationCode);
		
		if(userInfoMap != null) { 
			
			new ArenaShiftEventManager().addEvent(userInfoMap.get(ClientUserManager.USER_USERNAME), 
																											 new Date(), 
																											 "User is activated");
		}
		
		return userInfoMap;
	}
	
	@GET
	@Path("/sync/shifts/{username}/{dbVersion}")
	@UserSecure
	@Produces(MediaType.APPLICATION_JSON)
	public UpdateResponse syncShifts(@PathParam("username") String username,
																 @PathParam("dbVersion") String dbVersion) {
		
		
		new ArenaShiftEventManager().addEvent(username, new Date(), username + " connected to server");
		
		
		UpdateResponse updateResponse = new UpdateClientManager(
				new UpdateRequest(username, Long.valueOf(dbVersion))).getUpdateResponse();
		
		return updateResponse;
	}
	
	@PUT
	@Path("/sync/events/{username}")
	@UserSecure
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
