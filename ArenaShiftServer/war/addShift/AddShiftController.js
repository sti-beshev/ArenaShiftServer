/*
 * AddShiftController.js
 */

addShiftModule.controller("AddShiftController", ['$scope', function($scope) {
	
	$scope.addShift = addShift = {};
	$scope.addShift.yearChange = false;
	
	$scope.addShift.months = [
	                          {name: "Януари"}, {name: "Февуари"}, {name: "Март"}, {name: "Април"},
	                          {name: "Май"}, {name: "Юни"}, {name: "Юли"}, {name: "Август"},
	                          {name: "Септември"}, {name: "Октомври"}, {name: "Ноември"}, {name: "Декември"}
	                          ];
	$scope.addShift.days = [
	                        {number: "1"}, {number: "2"}, {number: "3"}, {number: "4"}, {number: "5"}, {number: "6"}, {number: "7"}, 
	                        {number: "8"}, {number: "9"}, {number: "10"}, {number: "11"}, {number: "12"}, {number: "13"}, {number: "14"}, 
	                        {number: "15"}, {number: "16"}, {number: "17"}, {number: "18"}, {number: "19"}, {number: "20"},
	                        {number: "21"}, {number: "22"}, {number: "23"}, {number: "24"}, {number: "25"}, {number: "26"}, {number: "27"}, 
	                        {number: "28"}, {number: "29"}, {number: "30"}, {number: "31"}
	                        ];
	$scope.addShift.mehanicy = [{name: "Александър"}, {name: "Венци"}, {name: "Иван"}, {name: "Стилиян"}];
	$scope.addShift.kasierky = [
	                        {name: "Галя"}, {name: "Гергана"}, {name: "Деси"}, {name: "Елица"},
	                        {name: "Жана"}, {name: "Катя"}, {name: "Цветелина"}
	                        ];
	$scope.addShift.kasierkyTreta = [
		                        {name: "няма"}, {name: "Галя"}, {name: "Гергана"}, {name: "Деси"}, 
		                        {name: "Елица"}, {name: "Жана"}, {name: "Катя"}, {name: "Цветелина"}
		                        ];
	$scope.addShift.razporeditely = [{name: "Бинка"}, {name: "Дафина"}, {name: "Наталия"}, {name: "Цеца"}];
	
	$scope.addShift.year = {
			
			name: "2015",
			changeYear: false
	};
	
	$scope.addShift.month = {	name: null,};
	
	$scope.addShift.day = {
		
			number: null,
			validate: function() {
				if(this.number === null) 
					return false;
				else
					return true;
		}
	};
	
	$scope.addShift.panMehanik = {
		
			name: null,
			valid: false
	};
	
	$scope.addShift.cenMehanik = {
			
			name: null,
			valid: false
	};
	
	$scope.addShift.razporeditelOne = {
			
			name: null,
			valid: false
	};
	
	$scope.addShift.razporeditelTwo = {
			
			name: null,
			valid: false
	};
	
	$scope.addShift.panKasaOne = {
			
			name: null,
			valid: false,
			validate: function() {
				
				if(this.name !== null) {
					
					this.valid = true;
					
					if($scope.addShift.panKasaTwo.name !== null) {
						if($scope.addShift.panKasaOne.name.name === $scope.addShift.panKasaTwo.name.name) {
							this.valid = false;
						}
					}
					
					if($scope.addShift.panKasaThree.name !== null) {
						if($scope.addShift.panKasaOne.name.name === $scope.addShift.panKasaThree.name.name) {
							this.valid = false;
						}
					}
					
					if($scope.addShift.cenKasa.name !== null) {
						if($scope.addShift.panKasaOne.name.name === $scope.addShift.cenKasa.name.name) {
							this.valid = false;
						}
					}
				}
			}
	};
	
	$scope.addShift.panKasaTwo = {
			
			name: null,
			valid: false,
			validate: function() {
				
				if(this.name !== null) {
					
					this.valid = true;
					
					if($scope.addShift.panKasaOne.name !== null) {
						if($scope.addShift.panKasaTwo.name.name === $scope.addShift.panKasaOne.name.name) {
							this.valid = false;
						}
					}
					
					if($scope.addShift.panKasaThree.name !== null) {
						if($scope.addShift.panKasaTwo.name.name === $scope.addShift.panKasaThree.name.name) {
							this.valid = false;
						}
					}
					
					if($scope.addShift.cenKasa.name !== null) {
						if($scope.addShift.panKasaTwo.name.name === $scope.addShift.cenKasa.name.name) {
							this.valid = false;
						}
					}
				}
			}
	};
	
	$scope.addShift.panKasaThree = {
			
			name: null,
			valid: false,
			validate: function() {
				
				if(this.name !== null) {
					
					this.valid = true;
					
					if($scope.addShift.panKasaOne.name !== null) {
						if($scope.addShift.panKasaThree.name.name === $scope.addShift.panKasaOne.name.name) {
							this.valid = false;
						}
					}
					
					if($scope.addShift.panKasaTwo.name !== null) {
						if($scope.addShift.panKasaThree.name.name === $scope.addShift.panKasaTwo.name.name) {
							this.valid = false;
						}
					}
					
					if($scope.addShift.cenKasa.name !== null) {
						if($scope.addShift.panKasaThree.name.name === $scope.addShift.cenKasa.name.name) {
							this.valid = false;
						}
					}
				}
			}
	};

	$scope.addShift.cenKasa = {
		
		name: null,
		valid: false,
		validate: function() {
			
			if(this.name !== null) {
				
				this.valid = true;
				
				if($scope.addShift.panKasaOne.name !== null) {
					if($scope.addShift.cenKasa.name.name === $scope.addShift.panKasaOne.name.name) {
						this.valid = false;
					}
				}
				
				if($scope.addShift.panKasaTwo.name !== null) {
					if($scope.addShift.cenKasa.name.name === $scope.addShift.panKasaTwo.name.name) {
						this.valid = false;
					}
				}
				
				if($scope.addShift.panKasaThree.name !== null) {
					if($scope.addShift.cenKasa.name.name === $scope.addShift.panKasaThree.name.name) {
						this.valid = false;
					}
				}
			}
		}
};
	
	$scope.addShift.checkShift = function() {
		
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
	
	$scope.addShift.checkMehanikForRepeat = function () {
		
		if($scope.addShift.panMehanik.name !== null && $scope.addShift.cenMehanik.name !== null) {
			
			if($scope.addShift.panMehanik.name.name === $scope.addShift.cenMehanik.name.name) {
				$scope.addShift.panMehanik.valid = false;
				$scope.addShift.cenMehanik.valid = false;
			}else{
				$scope.addShift.panMehanik.valid = true;
				$scope.addShift.cenMehanik.valid = true;
			}			
		}else {	
			
			if($scope.addShift.panMehanik.name !== null) {
				$scope.addShift.panMehanik.valid = true;
			}else{
				$scope.addShift.cenMehanik.valid = true;
			}
				
		}				
	};
	
$scope.addShift.checkRazporeditelForRepeat = function () {
		
		if($scope.addShift.razporeditelOne.name !== null && $scope.addShift.razporeditelTwo.name !== null) {
			
			if($scope.addShift.razporeditelOne.name.name === $scope.addShift.razporeditelTwo.name.name) {
				$scope.addShift.razporeditelOne.valid = false;
				$scope.addShift.razporeditelTwo.valid = false;
			}else{
				$scope.addShift.razporeditelOne.valid = true;
				$scope.addShift.razporeditelTwo.valid = true;
			}			
		}else {	
			
			if($scope.addShift.razporeditelOne.name !== null) {
				$scope.addShift.razporeditelOne.valid = true;
			}else{
				$scope.addShift.razporeditelTwo.valid = true;
			}
				
		}				
	};
	
	$scope.addShift.saveShift = function() {
		
		if($scope.addShift.checkShift() === true) {
			
			var shiftToSave = {};
			shiftToSave.year = $scope.addShift.year.name;
			shiftToSave.month = $scope.addShift.month.name.name;
			shiftToSave.day = $scope.addShift.day.number.number;
			shiftToSave.panMehanik = $scope.addShift.panMehanik.name.name;
			shiftToSave.panKasaOne = $scope.addShift.panKasaOne.name.name;
			shiftToSave.panKasaTwo = $scope.addShift.panKasaTwo.name.name;
			shiftToSave.panKasaThree = $scope.addShift.panKasaThree.name.name;
			shiftToSave.razporeditelOne = $scope.addShift.razporeditelOne.name.name;
			shiftToSave.razporeditelTwo = $scope.addShift.razporeditelTwo.name.name;
			shiftToSave.cenMehanik = $scope.addShift.cenMehanik.name.name;
			shiftToSave.cenKasa = $scope.addShift.cenKasa.name.name;
			
			console.log(shiftToSave);
			
		} else {
			window.alert("Грешно поле !!!");
		}
	};
	
}]);