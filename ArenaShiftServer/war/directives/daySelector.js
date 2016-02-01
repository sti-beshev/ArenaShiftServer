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
			hideShowButton: '=showbutton'	// Крие бутона 'Покажи' ако не е нужен.
		}
	};
});

directivesModule.controller('DaySelectorController', ['$scope', '$http', '$q', function($scope, $http, $q) {
	
	$scope.daySelector = daySelector = {};
	$scope.daySelector.shiftStatus = "Избери ден...";
	
	if($scope.hideShowButton) {
		$scope.daySelector.shiftStatus = "";
	}
	$scope.daySelector.yearChange = false;
	
	$scope.daySelector.months = [
		                         "Януари", "Февуари", "Март", "Април", "Май", "Юни","Юли", 
		                         "Август","Септември", "Октомври", "Ноември", "Декември"
		                         ];
	$scope.daySelector.days = [
		                        "1", "2", "3", "4", "5", "6", "7", 
		                        "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
		                        "21","22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
		                        ];			
	$scope.daySelector.year = {
				
			name: "2016",
			changeYear: false
	};
	
	/* Тези три функции служат да дадът достъп на други директиви до полетата на тази. 
	 * Конкретно 'peopleSelector' ги използва в момента. */
	this.getYear = function() {	return $scope.daySelector.year.name;		};
	this.getMonth = function() {	return $scope.daySelector.month.name;		};
	this.getDay = function() {	return $scope.daySelector.day.number;		};
	
	$scope.daySelector.showShift = function() {
		
		$scope.shiftToShow = {};
		$scope.shiftToShow.year = $scope.daySelector.year.name;
		$scope.shiftToShow.month = $scope.daySelector.month.name;
		$scope.shiftToShow.day = $scope.daySelector.day.number;
		var deferred = $q.defer()
		
		$http.post("/GetShiftServlet", $scope.shiftToShow).success(function(data) {
			
			deferred.resolve(data);
			
			deferred.promise.then(function(result) {
				
				if(result !== null) {
					
					$scope.daySelector.shiftStatus="";	// Няма грешка за изписване.
					
					/* Изпраща на всеки, който го интересува получената смяна. 
					 * Конкретно 'peopleSelector'.*/
					$scope.$broadcast('newShift', result);
					
				}else{
					$scope.daySelector.shiftStatus="Тази смяна е празна....";
				}
							
			}),
			function(err) {
				
				console.log(err);
			};
			
		}).error(function(reason) {
			
			deferred.reject(reason);
		});		
 }
}]);

