/*
 * peopeSelector.js
 */

directivesModule.directive("peopleSelector", function() {
	
	return {
		
		restrict : 'E',
		templateUrl: 'directives/peopleSelector.html',
		require: "^daySelector",
		controller: 'PeopleSelectorController',
		scope: {
			
			hideButtonAddShift: '=hidebuttonsave',	// Hides the button 'Покажи' if is not needed.
			hideButtonChangeShift: '=hidebuttonchange',	// Hides the button 'Промени' if is not needed.
			viewOnly: '=viewonly'	// The shift can't be edited.
		},
		link: function($scope, $elem, $attrs, daySelector) {
			
			// Gives access to 'daySelector' directive. 
		    $scope.daySelector = daySelector;
		}
	};
});

directivesModule.controller('PeopleSelectorController', ['$scope', '$http', '$q', 'Auth', 'WorkersService',
	function($scope, $http, $q, Auth, WorkersService) {
	
	$scope.peopleSelector = peopleSelector = {};
	
	$scope.peopleSelector.showSpinner = false;
	
	$scope.peopleSelector.hideShift = true;	// If the shifts is empty - hide it.
	$scope.peopleSelector.shiftStatus = "";
	
	// Don't hide the shift if the button 'Add Shift' is present.
	if($scope.hideButtonAddShift === false) {
		
		$scope.peopleSelector.hideShift = false;
	}

	
	$scope.peopleSelector.mehanicy = WorkersService.getMechanicNames(false);
	$scope.peopleSelector.kasierky = WorkersService.getConcessionNames(false);
	$scope.peopleSelector.kasierkyTreta = WorkersService.getConcessionNames(false);
	$scope.peopleSelector.razporeditely = WorkersService.getUsherNames(false);
	
	$scope.$watch('peopleSelector.kasierkyTreta', function() {
        
		if($scope.peopleSelector.kasierkyTreta !== undefined) {
			
			$scope.peopleSelector.kasierkyTreta.push('няма');
		}	
    });
	
	$scope.peopleSelector.panMehanik = {
		
			name: null,
			valid: false,
			validate: function(notFirstCheck) {
				
				// If the shift is received from the server there is no need to validate it.
				if(notFirstCheck) {
					
					$scope.peopleSelector.shiftStatus = "Смяната не е запаметена";
				}
				
				this.valid = false;
						
				if(this.name !== null) {
					
					this.valid = true;
				}
			}
	};
	
	$scope.peopleSelector.razporeditelOne = {
			
			name: null,
			valid: false
	};
	
	$scope.peopleSelector.razporeditelTwo = {
			
			name: null,
			valid: false
	};
	
	$scope.peopleSelector.panKasaOne = {
			
			name: null,
			valid: false,
			validate: function(notFirstCheck) {
				
				//If the shift is received from the server there is no need to validate it.
				if(notFirstCheck) {
					
					$scope.peopleSelector.shiftStatus = "Смяната не е запаметена";
				}
						
				if(this.name !== null) {
					
					this.valid = true;
					
					if($scope.peopleSelector.panKasaTwo.name !== null) {
						if($scope.peopleSelector.panKasaOne.name === $scope.peopleSelector.panKasaTwo.name) {
							this.valid = false;
						}
					}
					
					if($scope.peopleSelector.panKasaThree.name !== null) {
						if($scope.peopleSelector.panKasaOne.name === $scope.peopleSelector.panKasaThree.name) {
							this.valid = false;
						}
					}
					
				} else {
					this.valid = false;
				}
			}
	};
	
	$scope.peopleSelector.panKasaTwo = {
			
			name: null,
			valid: false,
			validate: function(notFirstCheck) {

				// If the shift is received from the server there is no need to validate it.
				if(notFirstCheck) {
					
					$scope.peopleSelector.shiftStatus = "Смяната не е запаметена";
				}
				
				if(this.name !== null) {
					
					this.valid = true;
					
					if($scope.peopleSelector.panKasaOne.name !== null) {
						if($scope.peopleSelector.panKasaTwo.name === $scope.peopleSelector.panKasaOne.name) {
							this.valid = false;
						}
					}
					
					if($scope.peopleSelector.panKasaThree.name !== null) {
						if($scope.peopleSelector.panKasaTwo.name === $scope.peopleSelector.panKasaThree.name) {
							this.valid = false;
						}
					}
					
				} else {
					this.valid = false;
				}
			}
	};
	
	$scope.peopleSelector.panKasaThree = {
			
			name: null,
			valid: false,
			validate: function(notFirstCheck) {

				// If the shift is received from the server there is no need to validate it.
				if(notFirstCheck) {
					
					$scope.peopleSelector.shiftStatus = "Смяната не е запаметена";
				}
				
				if(this.name !== null) {
					
					this.valid = true;
					
					if($scope.peopleSelector.panKasaOne.name !== null) {
						if($scope.peopleSelector.panKasaThree.name === $scope.peopleSelector.panKasaOne.name) {
							this.valid = false;
						}
					}
					
					if($scope.peopleSelector.panKasaTwo.name !== null) {
						if($scope.peopleSelector.panKasaThree.name === $scope.peopleSelector.panKasaTwo.name) {
							this.valid = false;
						}
					}
					
				} else {
					this.valid = false;
				}
			}
	};

	
	$scope.peopleSelector.checkShift = function() {
		
		if(this.panMehanik.valid === false) 
			return false;
		if(this.panKasaOne.valid === false) 
			return false;
		if(this.panKasaTwo.valid === false) 
			return false;
		if(this.panKasaThree.valid === false) 
			return false;
		if(this.razporeditelOne.valid === false) 
			return false;
		if(this.razporeditelTwo.valid === false) 
			return false;
		
		return true;
	};

	
	$scope.peopleSelector.panMehanikChanged = function() {
		
		$scope.peopleSelector.shiftStatus = "Смяната не е запаметена";
	}
	
	$scope.peopleSelector.checkKasaForRepeat = function(notFirstCheck) {
		
		$scope.peopleSelector.panKasaOne.validate(notFirstCheck);
		$scope.peopleSelector.panKasaTwo.validate(notFirstCheck);
		$scope.peopleSelector.panKasaThree.validate(notFirstCheck);
	}
	
	$scope.peopleSelector.checkRazporeditelForRepeat = function (notFirstCheck) {

		// If the shift is received from the server there is no need to validate it.
		if(notFirstCheck) {
			
			$scope.peopleSelector.shiftStatus = "Смяната не е запаметена";
		}
		
		$scope.peopleSelector.razporeditelOne.valid = true;
		$scope.peopleSelector.razporeditelTwo.valid = true;
		
		if($scope.peopleSelector.razporeditelOne.name === null) {
			$scope.peopleSelector.razporeditelOne.valid = false;
		}
		
		if($scope.peopleSelector.razporeditelTwo.name === null) {
			$scope.peopleSelector.razporeditelTwo.valid = false;
		}
		
		if($scope.peopleSelector.razporeditelOne.name === $scope.peopleSelector.razporeditelTwo.name) {
			
			$scope.peopleSelector.razporeditelOne.valid = false;
			$scope.peopleSelector.razporeditelTwo.valid = false;
		}
		
	};
	
	$scope.peopleSelector.addShift = function() {
		
		$scope.peopleSelector.saveShift("/rest/shift/add");
	}
	
	$scope.peopleSelector.changeShift = function() {
		
		$scope.peopleSelector.saveShift("/rest/shift/change");
	}
	
	$scope.peopleSelector.saveShift = function(httpAddress) {
		
		if($scope.peopleSelector.checkShift() === true) {
			
			$scope.peopleSelector.showSpinner = true;;
			
			var shiftToSave = {};
			
			shiftToSave.year = $scope.daySelector.getYear();
			shiftToSave.month = $scope.daySelector.getMonth();
			shiftToSave.day = $scope.daySelector.getDay();
			shiftToSave.panMehanik = $scope.peopleSelector.panMehanik.name;
			shiftToSave.panKasaOne = $scope.peopleSelector.panKasaOne.name;
			shiftToSave.panKasaTwo = $scope.peopleSelector.panKasaTwo.name;
			shiftToSave.panKasaThree = $scope.peopleSelector.panKasaThree.name;
			shiftToSave.razporeditelOne = $scope.peopleSelector.razporeditelOne.name;
			shiftToSave.razporeditelTwo = $scope.peopleSelector.razporeditelTwo.name;
			
			var deferred = $q.defer();
			
			$http.put(httpAddress, shiftToSave, {headers : Auth.getBasicAuthHeader()}).success(function(data) {
				
				$scope.peopleSelector.showSpinner = false;
						
				deferred.resolve(data);
				
				deferred.promise.then(function(result) {
					
					if(result.isError === false) {
						
						$scope.peopleSelector.hideShift = true;	// Hides the shift because is saved.
					}
					
					// Show the message from the server.
					$scope.peopleSelector.shiftStatus = result.message;	
					
				}),
				function(err) {
					
					console.log(err);
				};
				
			}).error(function(reason) {
				
				deferred.reject(reason);
			});
			
		} else {
			$scope.peopleSelector.shiftStatus = "Грешно поле !!!";
		}
	}
	
	// This function receives shift from other directives and shows it.
	$scope.$on('newShift', function(event, newShift) {
		
		$scope.peopleSelector.shiftStatus = "";
		
		if(newShift !== null) {
			
			peopleSelector.panMehanik.name = newShift.panMehanik;
			peopleSelector.panKasaOne.name = newShift.panKasaOne;
			peopleSelector.panKasaTwo.name = newShift.panKasaTwo;
			peopleSelector.panKasaThree.name = newShift.panKasaThree;
			peopleSelector.razporeditelOne.name = newShift.razporeditelOne;
			peopleSelector.razporeditelTwo.name = newShift.razporeditelTwo;
			
			peopleSelector.checkRazporeditelForRepeat(false);
			peopleSelector.checkKasaForRepeat(false);
			peopleSelector.panMehanik.validate(false);
			
			$scope.peopleSelector.hideShift = false;
			
		} else {
			
			$scope.peopleSelector.hideShift = true;
		}
	});
	
	// The date changed so hide the current shift.
	$scope.$on('dateChange', function(event, eventName) {
		
		$scope.peopleSelector.shiftStatus = "";
			
		peopleSelector.panMehanik.name = null;
		peopleSelector.panKasaOne.name = null;
		peopleSelector.panKasaTwo.name = null;
		peopleSelector.panKasaThree.name = null;
		peopleSelector.razporeditelOne.name = null;
		peopleSelector.razporeditelTwo.name = null;
		
		peopleSelector.checkRazporeditelForRepeat(false);
		peopleSelector.checkKasaForRepeat(false);
		peopleSelector.panMehanik.validate(false);
		
		$scope.peopleSelector.hideShift = false;
			
	});
	
	$scope.$on('workersLoadedFromServer', function(event, workerLabel, workersNamesArray) {
		
		if(workerLabel === "mechanic") {
			
			$scope.peopleSelector.mehanicy = workersNamesArray.slice();
		}
		
		if(workerLabel === "concession") {
			$scope.peopleSelector.kasierky = workersNamesArray.slice();
			$scope.peopleSelector.kasierkyTreta = workersNamesArray.slice();
			$scope.peopleSelector.kasierkyTreta.push("няма");
		}
		
		if(workerLabel === "usher") {
			$scope.peopleSelector.razporeditely = workersNamesArray.slice();
		}
	});
	
}]);