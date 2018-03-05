package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.shift.ChangeShiftManager;
import com.beshev.arenashiftserver.shift.Shift;
import com.google.gson.Gson;


@SuppressWarnings("serial")
public class ChangeShiftServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Shift shift = new Gson().fromJson(req.getReader(), Shift.class);
		
		ChangeShiftManager changeShiftManager = new ChangeShiftManager();
		ServerResponseMessage<String> serverResponseMessage = changeShiftManager.changeShift(shift);
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().write(new Gson().toJson(serverResponseMessage));
		
	}
}
