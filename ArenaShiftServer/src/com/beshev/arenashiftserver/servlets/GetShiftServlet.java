package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.shift.GetShiftManager;
import com.beshev.arenashiftserver.shift.Shift;
import com.beshev.arenashiftserver.shift.ShiftDate;
import com.google.gson.Gson;


@SuppressWarnings("serial")
public class GetShiftServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		ShiftDate shiftDate = new Gson().fromJson(req.getReader(), ShiftDate.class);
		
		ServerResponseMessage<Shift> serverResponseMessage = new GetShiftManager().getShift(shiftDate);
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    
	    resp.getWriter().write(new Gson().toJson(serverResponseMessage));
		
	}
}