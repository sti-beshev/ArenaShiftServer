/*
 * LoginController.js
 */

loginModule.controller("LoginController", ['$scope', '$http', '$q', '$location', 'Auth', 
                                           function($scope, $http, $q, $location, Auth) {
	
	$scope.loginManager = {};
	
	$scope.loginManager.showSpinner = false;
	
	$scope.loginManager.login = function() {
		
		$scope.loginManager.showSpinner = true;
		
		$scope.loginInfo = {
				
				username: $scope.loginManager.username,
				password: $scope.loginManager.password
		};
		
		var deferred = $q.defer();
		
		$http.post("/LoginServlet", $scope.loginInfo).success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				$scope.loginManager.showSpinner = false;
				
				if(result) {		
					
					Auth.loggedInTrue($scope.loginInfo.username, $scope.loginInfo.password);
					$location.path('/');
					
				}else{
					
					$scope.loginManager.password = "";
					$scope.loginManager.errorStatus = "Wrong username or password";
				}
				
			}),
			function(err) {
				
				console.log(err);
			};
			
		}).error(function(reason) {
			
			deferred.reject(reason);
		});		
	}
	
}]);