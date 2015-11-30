package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.GetShiftManager;
import com.beshev.arenashiftserver.Shift;
import com.beshev.arenashiftserver.ShiftDate;
import com.google.gson.Gson;


@SuppressWarnings("serial")
public class GetShiftServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		ShiftDate shiftDate = new Gson().fromJson(req.getReader(), ShiftDate.class);
		
		Shift shift = new GetShiftManager().getShift(shiftDate);
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    
	    resp.getWriter().write(new Gson().toJson(shift));
		
	}
}