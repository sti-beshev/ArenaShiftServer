package com.beshev.arenashiftserver.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.LoginInfo;
import com.google.gson.Gson;


@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		LoginInfo loginInfo = new Gson().fromJson(req.getReader(), LoginInfo.class);
		
		resp.setContentType("application/json");
	    resp.setCharacterEncoding("UTF-8");
	    
	    Boolean result = false;
	    
	    if(loginInfo.getUsername().equals("admin") && loginInfo.getPassword().equals("26dib26shift")) {
	    	result = true;
	    }
	    
	    resp.getWriter().write(new Gson().toJson(result));
		
	}
}