/*
 * UsersController.js
 */

usersModule.controller("UsersController", ['$scope', '$http', '$q', 
	function($scope, $http, $q) {
	
	$scope.userManager = {};
	
	$scope.userManager.showSpinner = false;
	
	$scope.userManager.inputAddUser = "";
	
	$scope.userManager.addUserStatus = "";
	
	$scope.userManager.addUserButtonClicked = function() {
		
		$scope.userManager.showSpinne = true;
		
		var newUserName = $scope.userManager.inputAddUser;
		
		var deferred = $q.defer();
		
		$http.post("/AddNewUserServlet", newUserName).success(function(data) {
			
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
	
}]);