package com.beshev.arenashiftserver.user;

import java.util.ArrayList;
import java.util.List;

import com.beshev.arenashiftserver.ServerResponseMessage;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class UserInfoManager {

	private DatastoreService datastore;

	public UserInfoManager() {

		datastore = DatastoreServiceFactory.getDatastoreService();
	}

	// Too simple to add unit test. If you add complexity, add a unit test. 
	public ClientUserInfo getUserInfo(String username) throws EntityNotFoundException {

		ClientUserInfo clientUserInfo = null;

		Entity userEntity = datastore.get(KeyFactory.createKey(ClientUserManager.USER_KIND, username));

		clientUserInfo = new ClientUserInfo(userEntity.getKey().getName(),
				userEntity.getProperty(ClientUserManager.CLIENT_USERNAME).toString(),
				userEntity.getProperty(ClientUserManager.CLIENT_PASSWORD).toString(),
				userEntity.getProperty(ClientUserManager.USER_ACTIVATION_CODE).toString());

		return clientUserInfo;
	}
	
	public ServerResponseMessage<List<WorkerInfo>> getAllWorkersInfo() {
		
		List<WorkerInfo> workersInfoList = new ArrayList<>();
		
		Query q = new Query(ClientUserManager.USER_KIND);
		
		PreparedQuery pq = datastore.prepare(q);
		
		List<Entity> usersEntityList = pq.asList(FetchOptions.Builder.withDefaults());
		
		for (Entity entity : usersEntityList) {
			
			workersInfoList.add(new WorkerInfo(entity.getKey().getName(), 
					(String)entity.getProperty(ClientUserManager.USER_LABEL), 
					(Boolean)entity.getProperty(ClientUserManager.USER_IS_WORKING)));
		}
		
		return new ServerResponseMessage<List<WorkerInfo>>("", false, workersInfoList);
	}
	
	public ServerResponseMessage<String> changeWorkerInfo(WorkerInfo workerInfo) {
		
		ServerResponseMessage<String> serverResponseMessage;
		
		try {
			
			Entity userEntity = datastore.get(KeyFactory.createKey(ClientUserManager.USER_KIND, workerInfo.getUsername()));
			userEntity.setProperty(ClientUserManager.USER_LABEL, workerInfo.getLabel());
			userEntity.setProperty(ClientUserManager.USER_IS_WORKING, workerInfo.isWorking());
			
			datastore.put(userEntity);
			
			serverResponseMessage = new ServerResponseMessage<String>("Status changed", false, null);
			
		} catch (EntityNotFoundException e) {
		
			serverResponseMessage = new ServerResponseMessage<String>("Status not changed...... server error", true, null);
		}
		
		return serverResponseMessage;
	}
	
	// Too simple to add unit test. If you add complexity, add a unit test. 
	public WorkerInfo getWorkerInfo(String username) throws EntityNotFoundException {
		
		WorkerInfo workerInfo = null;
		
		Entity workerEntity = datastore.get(KeyFactory.createKey(ClientUserManager.USER_KIND, username));
		
		workerInfo = new WorkerInfo(username, 
				(String)workerEntity.getProperty(ClientUserManager.USER_LABEL), 
				(boolean)workerEntity.getProperty(ClientUserManager.USER_IS_WORKING));
		
		return workerInfo;
	}

}
