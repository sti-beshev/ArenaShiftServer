package com.beshev.arenashiftserver.rest;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.beshev.arenashiftserver.event.ArenaShiftEventManager;
import com.beshev.arenashiftserver.update.UpdateClientManager;
import com.beshev.arenashiftserver.update.UpdateRequest;
import com.beshev.arenashiftserver.update.UpdateResponse;

@UserSecure
@Path("/sync")
public class SyncClientRestManager {

	@GET
	@Path("/shifts/{username}/{dbVersion}")
	@Produces(MediaType.APPLICATION_JSON)
	public UpdateResponse syncShifts(@PathParam("username") String username,
																 @PathParam("dbVersion") String dbVersion) {
		
		
		new ArenaShiftEventManager().addEvent(username, 
				 new Date(), 
               String.format("%s се свърза със сървъра.", username));
		
		
		UpdateResponse updateResponse = new UpdateClientManager(
				new UpdateRequest(username, Long.valueOf(dbVersion))).getUpdateResponse();
		
		return updateResponse;
	}
}
