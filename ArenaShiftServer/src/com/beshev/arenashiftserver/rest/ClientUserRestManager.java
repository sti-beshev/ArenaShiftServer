package com.beshev.arenashiftserver.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.beshev.arenashiftserver.user.ClientUserManager;

@Path("/user")
public class ClientUserRestManager {

	@GET
	@Path("/activate/{activationCode}")
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<String, String> activateUser(@PathParam("activationCode") String activationCode) {
		
		HashMap<String, String> userInfoMap = new ClientUserManager().activateUser(activationCode);
		
		return userInfoMap;
	}

}
