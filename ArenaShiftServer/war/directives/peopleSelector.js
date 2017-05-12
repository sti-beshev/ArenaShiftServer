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
			
			hideButtonSaveShift: '=hidebuttonsave',	// Крие бутона 'Добави' ако не е нужен.
			hideButtonChangeShift: '=hidebuttonchange',	// Крие бутона 'Промени' ако не е нужен.
			viewOnly: '=viewonly'	// Прави всички полета да не мога да бъдат променяни.
		},
		link: function($scope, $elem, $attrs, daySelector) {
			
			/* Това дава контролера на 'daySelector' за да мога чрез негови функции
			 * да имам достъп до полетата му. */
		    $scope.daySelector = daySelector;
		}
	};
});

directivesModule.controller('PeopleSelectorController', ['$scope', '$http', '$q', function($scope, $http, $q) {
	
	$scope.peopleSelector = peopleSelector = {};
	
	$scope.peopleSelector.showSpinner = false;
	
	$scope.peopleSelector.hideShift = true;	// Ако е празна крие смяната.
	$scope.peopleSelector.shiftStatus = "";
	
	// Ако е на страницата за добавяне на смяна не трябва да крие полетата.
	if($scope.hideButtonSaveShift === false) {
		
		$scope.peopleSelector.hideShift = false;
	}
	
	$scope.peopleSelector.mehanicy = ["Венци", "Стилиян"];
	$scope.peopleSelector.kasierky = ["Анелия", "Багряна", "Катя", "Наталия","Цветелина", "Боби"];
	$scope.peopleSelector.kasierkyTreta = ["няма", "Анелия", "Багряна", "Катя",  "Наталия","Цветелина", "Боби"];
	$scope.peopleSelector.razporeditely = ["Бинка", "Дафинела", "Кака", "Наталия", "Боби"];
	
	$scope.peopleSelector.panMehanik = {
		
			name: null,
			valid: false,
			validate: function(notFirstCheck) {
				
				/* Ако е първатя проверка няма смисъл да се сменя статуса защото e получено от
				 * сървъра където се знае смяната е валидна. */
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
				
				/* Ако е първатя проверка няма смисъл да се сменя статуса защото e получено от
				 * сървъра където се знае смяната е валидна. */
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

				/* Ако е първатя проверка няма смисъл да се сменя статуса защото e получено от
				 * сървъра където се знае смяната е валидна. */
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

				/* Ако е първатя проверка няма смисъл да се сменя статуса защото e получено от
				 * сървъра където се знае смяната е валидна. */
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

		/* Ако е първатя проверка няма смисъл да се сменя статуса защото e получено от
		 * сървъра където се знае смяната е валидна. */
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
	
	$scope.peopleSelector.saveShift = function() {
		
		if($scope.peopleSelector.checkShift() === true) {
			
			$scope.peopleSelector.showSpinner = true;
			
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
			
			$http.post("/AddShiftServlet", shiftToSave).success(function(data) {
				
				$scope.peopleSelector.showSpinner = false;
						
				deferred.resolve(data);
				
				deferred.promise.then(function(result) {
					
					$scope.peopleSelector.hideShift = true;	// Смяната е запаметена и се скрива.
					$scope.peopleSelector.shiftStatus = result.message;	// Показва съобщението от сървъра за статуса на запаметената смяна.
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
	
	$scope.peopleSelector.changeShift = function() {
		
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
			
			$http.post("/ChangeShiftServlet", shiftToSave).success(function(data) {
				
				$scope.peopleSelector.showSpinner = false;
						
				deferred.resolve(data);
				
				deferred.promise.then(function(result) {
					
					$scope.peopleSelector.hideShift = true;	// Смяната е запаметена и се скрива.
					$scope.peopleSelector.shiftStatus = result.message;	// Показва съобщението от сървъра за статуса на запаметената смяна.
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
	
	/* Тази функция слуша за получаване на нова смяна от друга директива
	 * и когато стане попълва полетата с нея. */
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
			
		}
	});
	
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
	
}]);