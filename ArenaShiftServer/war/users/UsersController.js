/*
 * UsersController.js
 */

usersModule.controller("UsersController", ['$scope', '$http', '$q', 'Auth',
	function($scope, $http, $q, Auth) {
	
	$scope.userManager = {};
	
	$scope.userManager.showSpinner = false;
	
	$scope.userManager.userActivationCode = "activation code";
	
	// Add User ******************************************************************************************
	
	$scope.userManager.inputAddUser = "";
	
	$scope.userManager.addUserStatus = "";
	
	$scope.userManager.workerLabels = ["visitor", "mechanic", "concession", "usher"];
	
	$scope.userManager.selectWorkerLabel = $scope.userManager.workerLabels[0];
	
	$scope.userManager.checkBoxIsWorking = true;
	
	$scope.userManager.addUserButtonClicked = function() {
		
		$scope.userManager.showSpinne = true;
		
		var newUserName = $scope.userManager.inputAddUser;
		
		var newUserInfo = {
				
				username: $scope.userManager.inputAddUser,
				
				label: $scope.userManager.selectWorkerLabel,
				
				isWorking: $scope.userManager.checkBoxIsWorking
		}
		
		var deferred = $q.defer();
		
		$http.put("/rest/user/worker/add/", newUserInfo, {headers : Auth.getBasicAuthHeader()})
		.success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				$scope.userManager.inputAddUser = "";
				
				$scope.userManager.showSpinne = false;
				
				$scope.userManager.addUserStatus = result.message;
				
			}),
			function(err) {
				
				console.log(err);
			};
			
		}).error(function(reason) {
			
			deferred.reject(reason);
		});		
	}
	
	// Change Status ************************************************************************************
	
	$scope.userManager.usersArray = [];
	
	$scope.userManager.selectUser = "";
	
	$scope.userManager.selectChangeWorkerLabel = $scope.userManager.workerLabels[0];
	
	$scope.userManager.checkBoxIsWorkingToChange = false;
	
	$scope.userManager.changeUserStatus = "";
	
	$scope.userManager.checkForChange = function() {
		
		let isChanged = false;
		
		$scope.userManager.usersArray.forEach(function(user) {
			
			if(user.username === $scope.userManager.selectUser.username) {
				
				if($scope.userManager.selectChangeWorkerLabel !== user.label) isChanged = true;
				
				if($scope.userManager.checkBoxIsWorkingToChange !== user.isWorking) isChanged = true;
			}
		});
		
		return isChanged;
	}

//*************************************************************************************************************************************************************
	$scope.userManager.changeStatusClicked = function() {
		
		if($scope.userManager.checkForChange()) {
			
			$scope.userManager.changeUserStatus = "Sending to server...";
			
			let workerInfo = {
					
					username : $scope.userManager.selectUser.username,
					
					label : $scope.userManager.selectChangeWorkerLabel,
					
					isWorking : $scope.userManager.checkBoxIsWorkingToChange
			}
			
			$scope.userManager.updateWorkerInfo(workerInfo);
			
		} else {
			
			$scope.userManager.changeUserStatus = "Nothing to change...";
		}
	}

//*************************************************************************************************************************************************************
	$scope.userManager.downloadAllWorkers = function() {
		
		$scope.userManager.showSpinne = true;	
		
		var deferred = $q.defer();
		
		$http.get("/rest/user/workers/", {headers : Auth.getBasicAuthHeader()}).success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				$scope.userManager.showSpinne = false;
				
				$scope.userManager.usersArray = result.payload;
				
			}),
			function(err) {
				
				$scope.userManager.showSpinne = false;
				console.log(err);
			};
			
		}).error(function(reason) {
			
			$scope.userManager.showSpinne = false;
			deferred.reject(reason);
		});		
	}
	
//*************************************************************************************************************************************************************
	$scope.userManager.downloadAllWorkers();
	
	$scope.userManager.userChanged = function() {
		
		$scope.userManager.usersArray.forEach(function(user) {
			
			if(user.username === $scope.userManager.selectUser.username) {
				
				$scope.userManager.selectChangeWorkerLabel = user.label;
				$scope.userManager.checkBoxIsWorkingToChange = user.isWorking;
				$scope.userManager.userActivationCode = user.activationCode;
			}
			
		});
	}
	
//*************************************************************************************************************************************************************	
	$scope.userManager.updateWorkerInfo = function(workerInfo) {
		
		$scope.userManager.showSpinne = true;	
		
		var deferred = $q.defer();
		
		$http.post("/rest/user/worker/update/", workerInfo, {headers : Auth.getBasicAuthHeader()})
		.success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				$scope.userManager.showSpinne = false;
				
				$scope.userManager.changeUserStatus  = result.message;
				
			}),
			function(err) {
				
				$scope.userManager.showSpinne = false;
				$scope.userManager.changeUserStatus  = result.message;
				console.log(err);
			};
			
		}).error(function(reason) {
			
			$scope.userManager.showSpinne = false;
			deferred.reject(reason);
		});		
	}
	
}]);