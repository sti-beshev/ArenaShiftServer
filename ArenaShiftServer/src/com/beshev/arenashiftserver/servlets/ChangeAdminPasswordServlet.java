package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.user.UserChangePassInfo;
import com.beshev.arenashiftserver.ServerMessage;
import com.beshev.arenashiftserver.user.AdminUserManager;
import com.google.gson.Gson;

public class ChangeAdminPasswordServlet extends HttpServlet  {

	
	private static final long serialVersionUID = -6695800006838120798L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		UserChangePassInfo userInfo = new Gson().fromJson(req.getReader(), UserChangePassInfo.class);
		
		String serverResponesMessage = new AdminUserManager().changeAdminPassword(userInfo);
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().write(new Gson().toJson(new ServerMessage(serverResponesMessage)));

	}
	
	
}