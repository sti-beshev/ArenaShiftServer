/*
 * getShiftController.js
 */

getShiftModule.controller("GetShiftController", ['$scope', '$http', '$q', function($scope, $http, $q) {
	
	$scope.getShift = addShift = {};
	$scope.getShift.yearChange = false;
	$scope.getShift.showShiftDiv = false;
	

	$scope.getShift.months = [
	                          {name: "Януари"}, {name: "Февуари"}, {name: "Март"}, {name: "Април"},
	                          {name: "Май"}, {name: "Юни"}, {name: "Юли"}, {name: "Август"},
	                          {name: "Септември"}, {name: "Октомври"}, {name: "Ноември"}, {name: "Декември"}
	                          ];
	$scope.getShift.days = [
	                        {number: "1"}, {number: "2"}, {number: "3"}, {number: "4"}, {number: "5"}, {number: "6"}, {number: "7"}, 
	                        {number: "8"}, {number: "9"}, {number: "10"}, {number: "11"}, {number: "12"}, {number: "13"}, {number: "14"}, 
	                        {number: "15"}, {number: "16"}, {number: "17"}, {number: "18"}, {number: "19"}, {number: "20"},
	                        {number: "21"}, {number: "22"}, {number: "23"}, {number: "24"}, {number: "25"}, {number: "26"}, {number: "27"}, 
	                        {number: "28"}, {number: "29"}, {number: "30"}, {number: "31"}
	                        ];
	
	
	$scope.getShift.year = {
			
			name: "2015",
			changeYear: false
	};

	$scope.getShift.month = {name: null};
	
	$scope.getShift.day = {number: null};
	
	$scope.getShift.showShift = function() {
		
		$scope.getShift.shiftToShow = {};
		$scope.getShift.shiftToShow.year = $scope.getShift.year.name;
		$scope.getShift.shiftToShow.month = $scope.getShift.month.name.name;
		$scope.getShift.shiftToShow.day = $scope.getShift.day.number.number;
		var deferred = $q.defer()
		
		$http.post("/GetShiftServlet", $scope.getShift.shiftToShow).success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				if(result !== null) {
					
					$scope.getShift.showShiftDiv = true;
					
					$scope.getShift.shiftToShow.panMehanik = result.panMehanik;
					$scope.getShift.shiftToShow.panKasaOne = result.panKasaOne;
					$scope.getShift.shiftToShow.panKasaTwo = result.panKasaTwo;
					$scope.getShift.shiftToShow.panKasaThree = result.panKasaThree;
					$scope.getShift.shiftToShow.razporeditelOne = result.razporeditelOne;
					$scope.getShift.shiftToShow.razporeditelTwo = result.razporeditelTwo;
					$scope.getShift.shiftToShow.cenMehanik = result.cenMehanik;
					$scope.getShift.shiftToShow.cenKasa = result.cenKasa;
					
				}else{
					$scope.getShift.showShiftDiv = false;
				}
							
			}),
			function(err) {
				
				console.log(err);
			};
			
		}).error(function(reason) {
			
			deferred.reject(reason);
		});
		
	};
	
}]);