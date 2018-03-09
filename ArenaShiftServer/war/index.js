/*
 * index.js
 */

var arenaShiftModule = angular.module("arenaShift", ['ngRoute', 'angularSpinner', 'authModule', 
	'directivesModule', 'loginModule', 'getShiftModule', 'addShiftModule', 'changeShiftModule', 
	'usersModule', 'settingsModule']);

arenaShiftModule.controller("MainController", ['$scope', function($scope) {
}]);

arenaShiftModule.run(['$rootScope', '$location', 'Auth', function ($rootScope, $location, Auth) {
    $rootScope.$on('$routeChangeStart', function (event) {
    	
    	if(!Auth.isAdmin()) {
    		
            $location.path('/login');
    	}
       
    });
}]);

arenaShiftModule.config(["$routeProvider", function($routeProvider) {
	
	$routeProvider
		.when("/", {
		
			templateUrl : "/getShift/getShift.html",
			controller : 'GetShiftController'
			
		})
		.when("/addShift", {
			
			templateUrl : "/addShift/addShift.html",
			controller : 'AddShiftController'
		
		})
		.when("/changeShift", {
			
			templateUrl : "/changeShift/changeShift.html",
			controller : 'ChangeShiftController'
		
		})
		.when("/users", {
			
			templateUrl : "/users/users.html",
			controller : 'UsersController'
		
		})
		.when("/settings", {
			
			templateUrl : "/settings/settings.html",
			controller : 'SettingsController'
		
		})
		.when("/login", {
			
			templateUrl : "/login/login.html",
			controller : 'LoginController'
		
		})
		.otherwise({
			redirectTo: '/'
	});
}]);