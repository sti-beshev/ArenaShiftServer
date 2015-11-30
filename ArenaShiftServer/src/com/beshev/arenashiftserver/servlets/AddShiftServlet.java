package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.AddShiftManager;
import com.beshev.arenashiftserver.Shift;
import com.beshev.arenashiftserver.ShiftStatus;
import com.google.gson.Gson;


@SuppressWarnings("serial")
public class AddShiftServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Shift shift = new Gson().fromJson(req.getReader(), Shift.class);
		
		AddShiftManager addShiftManager = new AddShiftManager();
		boolean status = addShiftManager.saveShift(shift);
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
		
		if(status) {
			resp.getWriter().write(new Gson().toJson(new ShiftStatus("Shift saved")));
		}else{
			resp.getWriter().write(new Gson().toJson(new ShiftStatus("ERROR: Shift not saved")));
		}
	}
}