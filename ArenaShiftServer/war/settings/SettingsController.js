/*
 * SettingsController.js
 */

settingsModule.controller("SettingsController", ['$scope', '$http', '$q', 'Auth',
	function($scope, $http, $q, Auth) {
	
	$scope.settingsManager = {}
	
	$scope.settingsManager.showSpinne = false;
	
	$scope.settingsManager.inputFirstPassword = "";
	$scope.settingsManager.inputConfirmPassword = "";
	$scope.settingsManager.inputCurrentPassword = "";
	
	$scope.settingsManager.changePasswordStatus = ""; 
	
	$scope.settingsManager.changePassword = function() {
		
		if($scope.settingsManager.verifyPasswordInputs()) {
			
			$scope.settingsManager.changePasswordStatus = "";
			
			$scope.settingsManager.sendPasswordToServer();
		}
	}
	
	$scope.settingsManager.verifyPasswordInputs = function() {
		
		var firstPass = $scope.settingsManager.inputFirstPassword;
		var secondPass = $scope.settingsManager.inputConfirmPassword;
		var currentPass = $scope.settingsManager.inputCurrentPassword;
		
		if(firstPass !== "" && secondPass !== "" && currentPass !== "") {
			
			if (firstPass === secondPass) {
				
				$scope.settingsManager.changePasswordStatus = "";
				
				return true;
				
			} else {
				
				$scope.settingsManager.changePasswordStatus = "Повторената парола е различна !";
			}
			
		} else {
			
			$scope.settingsManager.changePasswordStatus = "Никое поле не трябва да е празно !";
		}
		
		return false;
	}
	
	$scope.settingsManager.sendPasswordToServer = function() {
		
		$scope.settingsManager.showSpinne = true;
		
		var userInfo = {
				
				username: Auth.getUsername(),
				
				currentPassword: $scope.settingsManager.inputCurrentPassword,
				
				newPassword: $scope.settingsManager.inputFirstPassword
		}
		
		var deferred = $q.defer();
		
		$http.post("/ChangeAdminPasswordServlet", userInfo).success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				$scope.settingsManager.inputFirstPassword = "";
				$scope.settingsManager.inputConfirmPassword = "";
				$scope.settingsManager.inputCurrentPassword = "";
				
				$scope.settingsManager.showSpinne = false;
				
				$scope.settingsManager.changePasswordStatus = result.message;
				
			}),
			function(err) {
				
				console.log(err);
			};
			
		}).error(function(reason) {
			
			deferred.reject(reason);
		});		
	}
	
	
}]);