/**
 * 
 */
var app = angular.module("RSoftRMSApp", ['ui.grid','DDLDataModule','ngSanitize']); 

app.directive("loadList", function (){
	return {
		restrict : 'E',
		templateUrl : '../html/load-list.html'
	}
})


app.directive("showForm", function() {
	return {restrict: 'E',
	templateUrl :'../html/show-form.html'
	}
});

app.directive("dropdownList", function(){
	
	return {
		restrict : 'E' ,
		scope : {
			boundMembe : '=',
			listtype : '='
		},
		template : '<select ng-model="selectedCar" ng-options="x.area for x in ddlData">'
	}
});