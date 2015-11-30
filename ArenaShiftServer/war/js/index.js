/*
 * index.js
 */

var arenaShiftModule = angular.module("arenaShift", ['ngRoute', 'getShiftModule', 'addShiftModule']);

arenaShiftModule.controller("MainController", ['$scope', function($scope) {
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
		.otherwise({
			redirectTo: '/'
	});
}]);