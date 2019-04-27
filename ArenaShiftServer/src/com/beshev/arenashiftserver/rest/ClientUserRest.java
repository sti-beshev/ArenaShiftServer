package com.beshev.arenashiftserver.rest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.event.ArenaShiftEventManager;
import com.beshev.arenashiftserver.event.ArenaShiftUserEvent;
import com.beshev.arenashiftserver.update.UpdateClientManager;
import com.beshev.arenashiftserver.update.UpdateRequest;
import com.beshev.arenashiftserver.update.UpdateResponse;
import com.beshev.arenashiftserver.user.ClientUserManager;
import com.beshev.arenashiftserver.user.ExtendedWorkerInfo;
import com.beshev.arenashiftserver.user.UserInfoManager;
import com.beshev.arenashiftserver.user.UserLabelManager;
import com.beshev.arenashiftserver.user.WorkerInfo;

@Path("/user")
public class ClientUserRest {

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
	
	@GET
	@Path("/usernames/{label}")
	@AdminSecure
	@Produces(MediaType.APPLICATION_JSON)
	public ServerResponseMessage<List<String>> getUsernamesOfLabel(@PathParam("label") String userLabel) {
		
		return new UserLabelManager().getUsernamesOfLabel(userLabel);
	}
	
	@GET
	@Path("/workers")
	@AdminSecure
	@Produces(MediaType.APPLICATION_JSON)
	public ServerResponseMessage<List<ExtendedWorkerInfo>> getAllWorkersInfo() {
		
		return new UserInfoManager().getAllExtendedWorkersInfo();
	}
	
	@PUT
	@Path("/worker/add")
	@AdminSecure
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ServerResponseMessage<String> addWorker(WorkerInfo workerInfo) {
		
		ServerResponseMessage<String> serverResponesMessage;
		
		try {
			
			serverResponesMessage = new ClientUserManager().addClientUser(workerInfo);	
			
		} catch (IllegalArgumentException e) {
			
			serverResponesMessage = new ServerResponseMessage<String>("Username can not be empty !", true, null);
		}
		
		return serverResponesMessage;
	}
	
	@POST
	@Path("/worker/update")
	@AdminSecure
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ServerResponseMessage<String> updateWorkerInfo(WorkerInfo workerInfo) {
		
		return new UserInfoManager().changeWorkerInfo(workerInfo);
	}

}
