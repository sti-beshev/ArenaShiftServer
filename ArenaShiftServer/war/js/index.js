/*
 * index.js
 */

var arenaShiftModule = angular.module("arenaShift", ['ngRoute', 'authModule', 'directivesModule', 'loginModule', 'getShiftModule', 'addShiftModule', 'changeShiftModule']);

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
		.when("/login", {
			
			templateUrl : "/login/login.html",
			controller : 'LoginController'
		
		})
		.otherwise({
			redirectTo: '/'
	});
}]);