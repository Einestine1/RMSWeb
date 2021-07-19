/**
 * 
 */

var userModule = angular.module("DDLDataModule", []);
		   
userModule.factory('ddlService', ['$http', function ($http) {
	return {
    getDropDownListData : function (objTypeName) {
    	 return $http({
             url: '../DomainObjectsService?ObjType='+objTypeName,
             method: 'GET'
         });
    	}
    }    
}]);