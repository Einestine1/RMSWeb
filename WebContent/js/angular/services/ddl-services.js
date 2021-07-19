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
    	},
    postData : function (urlToPost,params)
    {
    	 var config = {
                 headers : {
                     'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
                	 //'Content-Type': 'application/json;charset=utf-8;'
                 }
             }
    	return $http.post(urlToPost,params,config);
    },
        getDataObject : function (urlToPost,params)
        {
        	 var config = {
                     headers : {
                         'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
                    	 //'Content-Type': 'application/json;charset=utf-8;'
                     }
                 }
        	 return $http({
                 url: urlToPost,
                 method: 'GET',
                 data:params
             });

        	 
        }

    }    
}]);