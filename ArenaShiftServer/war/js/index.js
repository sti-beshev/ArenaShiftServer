/*
 * index.js
 */

var arenaShiftModule = angular.module("arenaShift", ['ngRoute', 'shiftModule', 'addShiftModule']);

arenaShiftModule.controller("MainController", ['$scope', function($scope) {
}]);

arenaShiftModule.config(["$routeProvider", function($routeProvider) {
	
	$routeProvider
		.when("/", {
		
			templateUrl : "/shift/shift.html",
			controller : 'ShiftController'
			
		})
		.when("/addShift", {
			
			templateUrl : "/addShift/addShift.html",
			controller : 'AddShiftController'
		
		})
		.otherwise({
			redirectTo: '/'
	});
}]);