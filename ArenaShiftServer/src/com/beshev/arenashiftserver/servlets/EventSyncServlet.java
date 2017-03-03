package com.beshev.arenashiftserver.servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.event.ArenaShiftEventLog;
import com.beshev.arenashiftserver.event.ArenaShiftEventManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings("serial")
public class EventSyncServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		BufferedReader bufferedReader = null;
		ArenaShiftEventLog eventLog = null;
		
		try {
			
			bufferedReader = req.getReader();
			String textLine = "";
			String endMSG = "";
			
			while((textLine = bufferedReader.readLine()) != null) {
				endMSG += textLine;
			}
					
			bufferedReader.close();
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-DD HH:mm:ss.SSS").create();
			
			eventLog = gson.fromJson(endMSG, ArenaShiftEventLog.class);
			
			new ArenaShiftEventManager().addEventLog(eventLog);
			    			
		} catch (IOException e) {}
		
		if(bufferedReader != null) {
			bufferedReader.close();
		}
		
		
	}

}
