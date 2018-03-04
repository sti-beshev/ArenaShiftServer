package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.ServerMessage;
import com.beshev.arenashiftserver.user.ClientUserManager;
import com.google.gson.Gson;

public class AddNewUserServlet  extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		String newUsername = new Gson().fromJson(req.getReader(), String.class);
		
		String serverResponesMessage = "";
		
		try {		
			serverResponesMessage = new ClientUserManager().addClientUser(newUsername);	
			
		} catch (IllegalArgumentException e) {	
			serverResponesMessage = "Username can not be empty !";
		}
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().write(new Gson().toJson(new ServerMessage(serverResponesMessage)));

	}

}
