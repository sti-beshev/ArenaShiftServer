/*
 * service.js
 */ 

var servicesModule = angular.module("servicesModule", []);

servicesModule.factory('WorkersService', ['$rootScope','$http', '$q', 'Auth', 
	function($rootScope,$http, $q, Auth) {
	
	var mechanic = {
			namesArray: ['няма'],
			loadedFromServer: false
	};
	
	var concession = {
			namesArray: ['няма...'],
			loadedFromServer: false
	};
	
	var usher = {
			namesArray: ['няма...'],
			loadedFromServer: false
	};
	
	var getAllWorkerNamesFromLabel = function(workerLabel) {
		
		var deferred = $q.defer();
		
		$http.get("/rest/user/usernames/" + workerLabel, {headers : Auth.getBasicAuthHeader()})
			.success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				if(result.isError === false) {
					
					if(workerLabel === "mechanic") {
						
						mechanic.namesArray = result.payload;
						mechanic.loadedFromServer = true;
					}
					
					if(workerLabel === "concession") {
						
						concession.namesArray = result.payload;
						concession.loadedFromServer = true;
					}
					
					if(workerLabel === "usher") {
						
						usher.namesArray = result.payload;
						usher.loadedFromServer = true;
					}
					
					$rootScope.$broadcast('workersLoadedFromServer', workerLabel, result.payload);
					
				}else{
					
					console.log(result.message);
					
					$rootScope.$broadcast('workersLoadedFromServer', workerLabel, ['няма']);
				}
							
			}),
			function(err) {
				
				console.log(err);
			};
			
		}).error(function(reason) {
			
			deferred.reject(reason);
		});		
	}
	
	return {
		
		getMechanicNames: function(forceCheck) {
			
			if(mechanic.loadedFromServer === false || forceCheck) {
				
				getAllWorkerNamesFromLabel("mechanic");
				
				return ['Зареждам..'];
				
			} else { return mechanic.namesArray;}
		},
		
		getConcessionNames: function(forceCheck) {
			
			if(concession.loadedFromServer === false || forceCheck) {
				
				getAllWorkerNamesFromLabel("concession");
				
			} else { return concession.namesArray.slice();}
			
		},
		
		getUsherNames: function(forceCheck) {
			
			if(usher.loadedFromServer === false || forceCheck) {
				
				getAllWorkerNamesFromLabel("usher");
				
			} else { return usher.namesArray;}
		}
	}
}]);