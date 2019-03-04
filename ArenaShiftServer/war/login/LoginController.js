/*
 * LoginController.js
 */

loginModule.controller("LoginController", ['$scope', '$q', '$location', '$timeout', 'Auth', 
                                           function($scope, $q, $location, $timeout, Auth) {
	
	$scope.loginManager = {};
	
	$scope.loginManager.hideLoginForm = true;
	$scope.loginManager.actionStatus = "Logging in ...";
	$scope.loginManager.showSpinner = true;
	
	$scope.loginManager.login = function() {
		
		$scope.loginManager.showSpinner = true;
		
		$scope.loginInfo = {
				
				username: $scope.loginManager.username,
				password: $scope.loginManager.password
		};
		
		const loginPromis = Auth.login($scope.loginInfo);
		
		let deferred = $q.defer();
		
		loginPromis.success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				$scope.loginManager.showSpinner = false;
				
				if(result) {		
					
					Auth.loggedInTrue($scope.loginInfo.username, 
													$scope.loginInfo.password);
					
					if($scope.loginManager.rememberMe) {
						
						Auth.createCookie($scope.loginInfo.username, 
													   $scope.loginInfo.password);
					}
	
					$scope.$emit('loginEvent', true);
					$location.path('/');
					
				} else {
					
					$scope.loginManager.password = "";
					$scope.loginManager.actionStatus = "Wrong username or password";
				}
				
			}),
			function(err) {
				
				console.log(err);
			};
			
		});
	}
	
	$scope.init = function() {
		
		let cookiePromis = Auth.loginWithCookie();
		
		if(cookiePromis.cookie) {
			
			let deferred = $q.defer();
			
			cookiePromis.promis.success(function(data) {
				
				deferred.resolve(data);
				
				deferred.promise.then(function(result) {
					
					$scope.loginManager.showSpinner = false;
					
					if(result) {		
						
						Auth.loggedInTrue(cookiePromis.loginInfo.username, 
														cookiePromis.loginInfo.password);
						
						$scope.$emit('loginEvent', true);
						$location.path('/');
						
					} else {
						
						$scope.loginManager.hideLoginForm = false;
						$scope.loginManager.actionStatus = "";
						$scope.loginManager.showSpinner = false;
					}
					
				}),
				function(err) {
					
					console.log(err);
				};
				
			}).error(function(reason) {
				
				deferred.reject(reason);
			});	
			
		} else {
			
			$scope.loginManager.hideLoginForm = false;
			$scope.loginManager.actionStatus = "";
			$scope.loginManager.showSpinner = false;
		}
        
    };
	
}]);