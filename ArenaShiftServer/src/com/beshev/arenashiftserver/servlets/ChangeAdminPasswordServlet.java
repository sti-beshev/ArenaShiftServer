package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.user.AdminChangePassInfo;
import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.user.AdminUserManager;
import com.google.gson.Gson;

public class ChangeAdminPasswordServlet extends HttpServlet  {

	
	private static final long serialVersionUID = -6695800006838120798L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		AdminChangePassInfo userInfo = new Gson().fromJson(req.getReader(), AdminChangePassInfo.class);
		
		ServerResponseMessage<String> serverResponesMessage;
		
		try {		
			serverResponesMessage = new AdminUserManager().changeAdminPassword(userInfo);
			
		}catch (IllegalArgumentException e) {
			serverResponesMessage = new ServerResponseMessage<>("Password must be at least 6 characters long !", true, null);
		}
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().write(new Gson().toJson(serverResponesMessage));

	}
	
	
}