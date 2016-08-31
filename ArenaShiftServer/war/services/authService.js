/*  
 * authService.js
 */

var authModule = angular.module("authModule", []);

authModule.factory('Auth', function() {
	
	var signIN = false;
	
	return {
		
		isAdmin: function() {
			return signIN;
		},
		loggedInTrue: function() {
			signIN = true;
		}
	}
});