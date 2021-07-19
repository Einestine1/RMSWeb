/**
 * 
 */
app.controller('MainCtrl', ['$scope','$http', function ($scope , $http) {
	 
	
	$http.get('../getPlots?request_type=fetch')
    .success(function(data) {
       // $scope.gridOptions.data = data;
        $scope.myData = data;
    });
	

	}]);



























