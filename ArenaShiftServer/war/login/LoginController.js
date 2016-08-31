/*
 * LoginController.js
 */

loginModule.controller("LoginController", ['$scope', '$http', '$q', '$location', 'Auth', 
                                           function($scope, $http, $q, $location, Auth) {
	
	$scope.loginManager = {};
	
	$scope.loginManager.login = function() {
		
		$scope.loginInfo = {
				
				username: $scope.loginManager.username,
				password: $scope.loginManager.password
		};
		
		var deferred = $q.defer();
		
		$http.post("/LoginServlet", $scope.loginInfo).success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				if(result) {		
					
					Auth.loggedInTrue();
					$location.path('/');
					
				}else{
					//Грешна парола или юзърнайм
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