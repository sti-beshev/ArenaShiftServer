package com.beshev.arenashiftserver.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.beshev.arenashiftserver.shift.AddShiftManager;
import com.beshev.arenashiftserver.shift.ChangeShiftManager;
import com.beshev.arenashiftserver.shift.GetShiftManager;
import com.beshev.arenashiftserver.shift.Shift;
import com.beshev.arenashiftserver.shift.ShiftDate;
import com.beshev.arenashiftserver.shift.ShiftStatus;

@Path("/shift")
public class ShiftRestManager {

	public ShiftRestManager() {
		
	}
	
	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ShiftStatus addShift(Shift shift) {
		
		AddShiftManager addShiftManager = new AddShiftManager();
		String status = addShiftManager.saveShift(shift);
		
		return new ShiftStatus(status);
	}
	
	@GET
	@Path("/get/{year}/{month}/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Shift getShift(@PathParam("year") String year,
										@PathParam("month") String month,
										@PathParam("day") String day) {
		
		ShiftDate shiftDate = new ShiftDate(Integer.parseInt(year), 
																	 Integer.parseInt(month), 
																	 Integer.parseInt(day));
				
		Shift shift = new GetShiftManager().getShift(shiftDate);
		
		return shift;
	}
	
	@PUT
	@Path("/change")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ShiftStatus changeShift(Shift shift) {
		
		ChangeShiftManager changeShiftManager = new ChangeShiftManager();
		String status = changeShiftManager.changeShift(shift);
		
		return new ShiftStatus(status);
	}

}
