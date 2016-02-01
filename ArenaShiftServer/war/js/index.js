/*
 * index.js
 */

var arenaShiftModule = angular.module("arenaShift", ['ngRoute', 'directivesModule', 'getShiftModule', 'addShiftModule', 'changeShiftModule']);

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
		.when("/changeShift", {
			
			templateUrl : "/changeShift/changeShift.html",
			controller : 'ChangeShiftController'
		
		})
		.otherwise({
			redirectTo: '/'
	});
}]);