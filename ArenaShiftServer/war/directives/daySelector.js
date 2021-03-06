/*
 * daySelector.js
 */

directivesModule.directive("daySelector", function() {
	
	return {
		
		restrict : 'E',
		templateUrl: 'directives/daySelector.html',
		controller: 'DaySelectorController',
		transclude: true,
		scope: {	
			hideShowButton: '=showbutton'	// Hides the button 'Покажи' if is not needed.
		}
	};
});

directivesModule.controller('DaySelectorController', ['$scope', '$http', '$q', 'Auth', function($scope, $http, $q, Auth) {
	
	$scope.daySelector = daySelector = {};
	$scope.daySelector.showSpinner = false;
	$scope.daySelector.shiftStatus = "Избери ден...";
	
	if($scope.hideShowButton) {
		$scope.daySelector.shiftStatus = "";
	}
	$scope.daySelector.yearChange = false;
	
	$scope.daySelector.months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
	
	$scope.daySelector.days = [
		                        1, 2, 3, 4, 5, 6, 7, 
		                        8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
		                        21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31
		                        ];			
	$scope.daySelector.year = {
				
			name: 2019,
			changeYear: false
	};
	
	/* This functions give access to  this variables  from other directives.   */
	this.getYear = function() {	return $scope.daySelector.year.name;		};
	this.getMonth = function() {	return $scope.daySelector.month.name;		};
	this.getDay = function() {	return $scope.daySelector.day.number;		};
	
	$scope.daySelector.showShift = function() {
		
		$scope.daySelector.showSpinner = true;
		
		$scope.shiftToShow = {};
		$scope.shiftToShow.year = $scope.daySelector.year.name;
		$scope.shiftToShow.month = $scope.daySelector.month.name;
		$scope.shiftToShow.day = $scope.daySelector.day.number;
		
		shiftDate = $scope.daySelector.year.name + "/" + 
							$scope.daySelector.month.name + "/" +
							$scope.daySelector.day.number;
		
		var deferred = $q.defer();
		
		$http.get("/rest/shift/get/" + shiftDate, {headers : Auth.getBasicAuthHeader()}).success(function(data) {
			
			$scope.daySelector.showSpinner = false;
			
			$scope.daySelector.shiftStatus = "";
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				if(result.isError === false) {	
					
					$scope.daySelector.shiftStatus="";	// No errors to show.
					
					/* Broadcast the recived shift. Curently 'peopleSelector' depends on it. */
					$scope.$broadcast('newShift', result.payload);
					
				}else{
					
					$scope.daySelector.shiftStatus=result.message;
					
					result = null;
				}
							
			}),
			function(err) {
				
				console.log(err);
			};
			
		}).error(function(reason) {
			
			deferred.reject(reason);
		});		
 }
	
	$scope.daySelector.dateChange = function() {
		
		$scope.daySelector.shiftStatus = "";
		
		$scope.$broadcast('dateChange',  "");
	}
}]);

