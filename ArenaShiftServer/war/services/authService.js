/*  
 * authService.js
 */

var authModule = angular.module("authModule", []);

authModule.factory('Auth', function() {
	
	var signIN = false;
	
	var username = "";
	
	var password = "";
	
	return {
		
		isAdmin: function() {
			return signIN;
		},
		loggedInTrue: function(loginUsername, loginPassword) {
			
			signIN = true;
			
			username = loginUsername;
			
			password = loginPassword;
			
		},
		getBasicAuthHeader: function() {
			
			if(signIN) {
				return {"Authorization": "Basic " + window.btoa(username + ":" + password)}
			}
		},
		getUsername: function() {
			return username;
		}
	}
});