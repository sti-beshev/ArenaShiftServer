package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.ChangeShiftManager;
import com.beshev.arenashiftserver.Shift;
import com.beshev.arenashiftserver.ShiftStatus;
import com.google.gson.Gson;


@SuppressWarnings("serial")
public class ChangeShiftServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Shift shift = new Gson().fromJson(req.getReader(), Shift.class);
		
		ChangeShiftManager changeShiftManager = new ChangeShiftManager();
		String status = changeShiftManager.changeShift(shift);
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().write(new Gson().toJson(new ShiftStatus(status)));
		
	}
}
