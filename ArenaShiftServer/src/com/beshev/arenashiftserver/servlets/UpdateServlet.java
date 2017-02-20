package com.beshev.arenashiftserver.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beshev.arenashiftserver.event.ArenaShiftEventManager;
import com.beshev.arenashiftserver.update.UpdateClientManager;
import com.beshev.arenashiftserver.update.UpdateRequest;
import com.beshev.arenashiftserver.update.UpdateResponse;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class UpdateServlet  extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		UpdateRequest updateReques = null;
		UpdateResponse updateResponse = null;
		
		try {
			
			bufferedReader = req.getReader();
			String textLine = "";
			String endMSG = "";
			
			while((textLine = bufferedReader.readLine()) != null) {
				endMSG += textLine;
			}
					
			bufferedReader.close();
			
			updateReques = new Gson().fromJson(endMSG, UpdateRequest.class);
			
			new ArenaShiftEventManager().addEvent(updateReques.getUserName(), new Date(), 
					updateReques.getUserName() + " се свърза със сървъра.");
			
			updateResponse = new UpdateClientManager(updateReques).getUpdateResponse();
			
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			bufferedWriter = new BufferedWriter(resp.getWriter());
			bufferedWriter.write(new Gson().toJson(updateResponse));
		    bufferedWriter.flush();
		    bufferedWriter.close();
		    			
		} catch (IOException e) {}
		
		if(bufferedReader != null) {
			bufferedReader.close();
		}
		
		if(bufferedWriter != null) {
			bufferedWriter.close();
		}
		
		if(updateReques != null) {
			
			
		}
	}
}
