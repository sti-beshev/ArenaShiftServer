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
	
	$scope.peopleSelector.hideShift = true;	// Ако е празна крие смяната.
	$scope.peopleSelector.shiftStatus = "";
	
	// Ако е на страницата за добавяне на смяна не трябва да крие полетата.
	if($scope.hideButtonSaveShift === false) {
		
		$scope.peopleSelector.hideShift = false;
	}
	
	$scope.peopleSelector.mehanicy = ["Александър", "Венци", "Иван", "Стилиян"];
	$scope.peopleSelector.kasierky = ["Гергана", "Деси", "Елица", "Жана", "Катя", "Наталия","Цветелина"];
	$scope.peopleSelector.kasierkyTreta = ["няма", "Гергана", "Деси", "Елица", "Жана", "Катя", "Наталия","Цветелина"];
	$scope.peopleSelector.razporeditely = ["Бинка", "Дафина", "Наталия", "Цеца"];
	
	$scope.peopleSelector.panMehanik = {
		
			name: null,
			valid: false
	};
	
	$scope.peopleSelector.cenMehanik = {
			
			name: null,
			valid: false
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
					
					if($scope.peopleSelector.cenKasa.name !== null) {
						if($scope.peopleSelector.panKasaOne.name === $scope.peopleSelector.cenKasa.name) {
							this.valid = false;
						}
					}
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
					
					if($scope.peopleSelector.cenKasa.name !== null) {
						if($scope.peopleSelector.panKasaTwo.name === $scope.peopleSelector.cenKasa.name) {
							this.valid = false;
						}
					}
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
					
					if($scope.peopleSelector.cenKasa.name !== null) {
						if($scope.peopleSelector.panKasaThree.name === $scope.peopleSelector.cenKasa.name) {
							this.valid = false;
						}
					}
				}
			}
	};

	$scope.peopleSelector.cenKasa = {
		
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
					if($scope.peopleSelector.cenKasa.name === $scope.peopleSelector.panKasaOne.name) {
						this.valid = false;
					}
				}
				
				if($scope.peopleSelector.panKasaTwo.name !== null) {
					if($scope.peopleSelector.cenKasa.name === $scope.peopleSelector.panKasaTwo.name) {
						this.valid = false;
					}
				}
				
				if($scope.peopleSelector.panKasaThree.name !== null) {
					if($scope.peopleSelector.cenKasa.name === $scope.peopleSelector.panKasaThree.name) {
						this.valid = false;
					}
				}
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
		if(this.cenMehanik.valid === false) 
			return false;
		if(this.cenKasa.valid === false) 
			return false;
		
		return true;
	};
	
	$scope.peopleSelector.checkMehanikForRepeat = function (notFirstCheck) {

		/* Ако е първатя проверка няма смисъл да се сменя статуса защото e получено от
		 * сървъра където се знае смяната е валидна. */
		if(notFirstCheck) {
			
			$scope.peopleSelector.shiftStatus = "Смяната не е запаметена";
		}
		
		if($scope.peopleSelector.panMehanik.name !== null && $scope.peopleSelector.cenMehanik.name !== null) {
			
			if($scope.peopleSelector.panMehanik.name === $scope.peopleSelector.cenMehanik.name) {
				$scope.peopleSelector.panMehanik.valid = false;
				$scope.peopleSelector.cenMehanik.valid = false;
			}else{
				$scope.peopleSelector.panMehanik.valid = true;
				$scope.peopleSelector.cenMehanik.valid = true;
			}			
		}else {	
			
			if($scope.peopleSelector.panMehanik.name !== null) {
				$scope.peopleSelector.panMehanik.valid = true;
			}else{
				$scope.peopleSelector.cenMehanik.valid = true;
			}
				
		}				
	};
	
	$scope.peopleSelector.checkKasaForRepeat = function(notFirstCheck) {
		
		$scope.peopleSelector.panKasaOne.validate(notFirstCheck);
		$scope.peopleSelector.panKasaTwo.validate(notFirstCheck);
		$scope.peopleSelector.panKasaThree.validate(notFirstCheck);
		$scope.peopleSelector.cenKasa.validate(notFirstCheck);
	}
	
	$scope.peopleSelector.checkRazporeditelForRepeat = function (notFirstCheck) {

		/* Ако е първатя проверка няма смисъл да се сменя статуса защото e получено от
		 * сървъра където се знае смяната е валидна. */
		if(notFirstCheck) {
			
			$scope.peopleSelector.shiftStatus = "Смяната не е запаметена";
		}
		
		if($scope.peopleSelector.razporeditelOne.name !== null && $scope.peopleSelector.razporeditelTwo.name !== null) {
			
			if($scope.peopleSelector.razporeditelOne.name === $scope.peopleSelector.razporeditelTwo.name) {
				$scope.peopleSelector.razporeditelOne.valid = false;
				$scope.peopleSelector.razporeditelTwo.valid = false;
			}else{
				$scope.peopleSelector.razporeditelOne.valid = true;
				$scope.peopleSelector.razporeditelTwo.valid = true;
			}			
		}else {	
			
			if($scope.peopleSelector.razporeditelOne.name !== null) {
				$scope.peopleSelector.razporeditelOne.valid = true;
			}else{
				$scope.peopleSelector.razporeditelTwo.valid = true;
			}
				
		}				
	};
	
	$scope.peopleSelector.saveShift = function() {
		
		if($scope.peopleSelector.checkShift() === true) {
			
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
			shiftToSave.cenMehanik = $scope.peopleSelector.cenMehanik.name;
			shiftToSave.cenKasa = $scope.peopleSelector.cenKasa.name;
			
			var deferred = $q.defer();
			
			$http.post("/AddShiftServlet", shiftToSave).success(function(data) {
						
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
			shiftToSave.cenMehanik = $scope.peopleSelector.cenMehanik.name;
			shiftToSave.cenKasa = $scope.peopleSelector.cenKasa.name;
			
			var deferred = $q.defer();
			
			$http.post("/ChangeShiftServlet", shiftToSave).success(function(data) {
						
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
		
		if(newShift !== null) {
			
			peopleSelector.panMehanik.name = newShift.panMehanik;
			peopleSelector.panKasaOne.name = newShift.panKasaOne;
			peopleSelector.panKasaTwo.name = newShift.panKasaTwo;
			peopleSelector.panKasaThree.name = newShift.panKasaThree;
			peopleSelector.razporeditelOne.name = newShift.razporeditelOne;
			peopleSelector.razporeditelTwo.name = newShift.razporeditelTwo;
			peopleSelector.cenMehanik.name = newShift.cenMehanik;
			peopleSelector.cenKasa.name = newShift.cenKasa;
			
			peopleSelector.checkMehanikForRepeat(false);
			peopleSelector.checkRazporeditelForRepeat(false);
			peopleSelector.checkKasaForRepeat(false);
			
			$scope.peopleSelector.hideShift = false;
			
		} else {
			$scope.peopleSelector.hideShift = true;
		}
		
		
		
	});
	
}]);