/*  
 * authService.js
 */

var authModule = angular.module("authModule", []);

authModule.factory('Auth', ['$http', '$q', '$cookies', function($http, $q, $cookies) {
	
	var signIN = false;
	
	var username = "";
	
	var password = "";
	
	return {
		
		isAdmin: function() {
			
			return signIN;
		},
		loginWithCookie: function() {
			
			resultPromis = {
					
					cookie: false
			};
			
			loginInfo = {
					
					username: $cookies.get("username"),
					password: $cookies.get("password")
			};
			
			if(loginInfo.username !== undefined && loginInfo.password !== undefined) {
				
				resultPromis.cookie = true;
				
				resultPromis.loginInfo = loginInfo;
				
				resultPromis.promis = this.login(loginInfo);
			}
			
			return resultPromis;
		},
		login: function(loginInfo) {
			
			return $http.post("/LoginServlet", loginInfo);
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
		},
		createCookie: function(username, password)
		{
			$cookies.put('username', username, {expires: "Thu, 18 Dec 2050 12:00:00 UTC"});
			$cookies.put('password', password, {expires: "Thu, 18 Dec 2050 12:00:00 UTC"});
		},
		logout: function() {
			
			signIN = false;
            username = "";
			password = "";
			$cookies.remove('username');
			$cookies.remove('password');
		}
	}
}]);