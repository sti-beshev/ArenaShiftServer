package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.beshev.arenashiftserver.user.ClientUserManager;
import com.beshev.arenashiftserver.user.WorkerInfo;
import com.google.gson.Gson;

/**
 * LEGACY - not in use!!!
 */
public class AddNewUserServlet  extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		boolean haveError = false;
		
		WorkerInfo workerInfo = new Gson().fromJson(req.getReader(), WorkerInfo.class);
		
		ServerResponseMessage<String> serverResponesMessage;
		
		try {
			
			serverResponesMessage = new ClientUserManager().addClientUser(workerInfo);	
			
		} catch (IllegalArgumentException e) {
			
			haveError = true;
			serverResponesMessage = new ServerResponseMessage<String>("Username can not be empty !", haveError, null);
		}
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
		
		resp.getWriter().write(new Gson().toJson(serverResponesMessage));

	}

}
