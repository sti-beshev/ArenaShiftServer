package com.beshev.arenashiftserver.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.shift.AddShiftManager;
import com.beshev.arenashiftserver.shift.ChangeShiftManager;
import com.beshev.arenashiftserver.shift.GetShiftManager;
import com.beshev.arenashiftserver.shift.Shift;
import com.beshev.arenashiftserver.shift.ShiftDate;

@Path("/shift")
@AdminSecure
public class ShiftRest {

	public ShiftRest() {
		
	}
	
	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ServerResponseMessage<String> addShift(Shift shift) {
		
		AddShiftManager addShiftManager = new AddShiftManager();
		ServerResponseMessage<String> serverResponseMessage = addShiftManager.saveShift(shift);
		
		return serverResponseMessage;
	}
	
	@GET
	@Path("/get/{year}/{month}/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public ServerResponseMessage<Shift> getShift(@PathParam("year") String year,
										@PathParam("month") String month,
										@PathParam("day") String day) {
		
		ShiftDate shiftDate = new ShiftDate(Integer.parseInt(year), 
																	 Integer.parseInt(month), 
																	 Integer.parseInt(day));
				
		ServerResponseMessage<Shift> serverResponseMessage = new GetShiftManager().getShift(shiftDate);
		
		return serverResponseMessage;
	}
	
	@PUT
	@Path("/change")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ServerResponseMessage<String> changeShift(Shift shift) {
		
		ChangeShiftManager changeShiftManager = new ChangeShiftManager();
		ServerResponseMessage<String> serverResponseMessage = changeShiftManager.changeShift(shift);
		
		return serverResponseMessage;
	}

}
