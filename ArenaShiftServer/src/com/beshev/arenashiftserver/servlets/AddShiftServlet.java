package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.shift.AddShiftManager;
import com.beshev.arenashiftserver.shift.Shift;
import com.google.gson.Gson;

/**
 * LEGACY - not in use!!!
 */
@SuppressWarnings("serial")
public class AddShiftServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Shift shift = new Gson().fromJson(req.getReader(), Shift.class);
		
		AddShiftManager addShiftManager = new AddShiftManager();
		ServerResponseMessage<String> serverResponseMessage = addShiftManager.saveShift(shift);
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().write(new Gson().toJson(serverResponseMessage));
		
	}
}