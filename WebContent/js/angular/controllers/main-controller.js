/**
 * 
 */
app.controller("MainController",['$scope','ddlService','$window','$sce','$timeout', function($scope,ddlService,$window,$sce,$timeout){
	
	
	$scope.datavalue = {};
	$scope.done = false;
	this.currentForm = "Plot";//default object type
	
	$scope.crntFrm = "Plot";//default object type (this is just to expose this.currentForm at $scope level) and is set by 
							//this.currentForm's setter function 
	$scope.selectedFields = [];
	
	$scope.selectedChkboxs = [];
	$scope.listOfAllCheckboxNames = [];
	
	$scope.selectedPasswords1 = [];
	$scope.selectedPasswords2 = [];
	$scope.listOfAllPasswordNames = [];

	
	$scope.selectedRadios = [];
	$scope.listOfAllRadioNames = [];
	
	$scope.strRequestResults = "";
	
	$scope.AllFields = [];
	
	$scope.sequenceList = [];//this array is used to maintain all ddl names, each name maps to the corresponding select 
							 //options ddl list in $scope.listOfDataforAllDDLs = []; 
							 //see : ng-model="selectedFields[sequenceList.indexOf(elmnt.objType)]" in the binding Html
							 //we push the ddl names in this $scope.sequenceList at the same time when a new ddl data is added
							 //see : function $scope.callGetforDDLs
	$scope.listOfDataforAllDDLs = [];
	
	$scope.listOfNamesforAllDDLs = [];
	$scope.listOfAllFieldNames = [];
	
	
	this.jsonString = "";
	
	
	$scope.getListofAllDDLs = function (){
		return $scope.listOfDataforAllDDLs;
	}
	
	this.getDDLData = function(ddlName)
	{
		return $scope.listOfDataforAllDDLs[ddlName];
	}
	$scope.getDDLVisibleField = function(ddlName)
	{
		return $scope.listOfDataforAllDDLs[ddlName].visibleField;
	}
	
	$scope.getCurrentFormMeta = function(){//This function returns metadata for current selected form 
		return $scope.colModels[$scope.crntFrm.toLowerCase()];
	}
	
	$scope.getFieldType = function(fieldName)
	{
		colDef = $scope.getCurrentFormMeta();

		if(colDef)
		{
			var colObj = colDef;
			
			for(objIndex in colObj){
				var theObj = colObj[objIndex];
			
				if(theObj.objType==fieldName)//if the column in the metamodel is select then 
				{
					return theObj.dataType;
				}
			}
		}
		return null;
	}
	
	
	//This function loads the data for each unique DDL name in the $scope.listOfNamesforAllDDLs 
	//IMPORTANT: this function should be called after calling $scope.loadDDLNames otherwise the names list will be empty 
	//			 and no data will be loaded.
	$scope.loadDDLData = function() {
		if($scope.listOfDataforAllDDLs.length>0)
			{
			return ;
			}
		for (ddlNm in $scope.listOfNamesforAllDDLs) {
				this.callGetforDDLs($scope.listOfNamesforAllDDLs[ddlNm]);
		}
	}
	
	//This function builds a list of unique names of the Objects to be loaded in the Drop down list
	//This way we get the required unique names of all the DDL objects to be loaded from the server
	$scope.loadDDLNames = function(){
		if($scope.listOfNamesforAllDDLs.length>0) {
				return;
			}
		var retList = [];
		var retListAllFieldNames = [];//[[],[]];
		var retListAllChkboxNames =[];
		var retListAllRadioNames =[];
		var retListAllPasswordNames = [];
//			retListAllFieldNames.push('text');
//			retListAllFieldNames.push('radio');
		colDef = $scope.getCurrentFormMeta();

		if(colDef)
		{
			var colObj = colDef;
			
			for(objIndex in colObj){
				var theObj = colObj[objIndex];
				
				if(theObj.inputType=='select')//if the column in the metamodel is select then 
				{

					if(retList.indexOf(theObj.objType)<0)
					{
						retList.push(theObj.objType);
					}
				}
				
				else if(retListAllFieldNames.indexOf(theObj.dataIndx)<0)
					{
						if(theObj.inputType=='text')
						{	
							retListAllFieldNames.push(theObj.dataIndx);
						}
						if(theObj.inputType=='checkbox')
							{
								if(retListAllChkboxNames.indexOf(theObj.dataIndx)<0)
									{
										retListAllChkboxNames.push(theObj.dataIndx);
									}
							}
						else if (theObj.inputType=='radio')
							{
								if(retListAllRadioNames.indexOf(theObj.dataIndx)<0)
									{
										retListAllRadioNames.push(theObj.dataIndx);
									}
							}
						else if (theObj.inputType=='password'){
								if(retListAllPasswordNames.indexOf(theObj.dataIndx)<0)
									{
										retListAllPasswordNames.push(theObj.dataIndx);
									}
						}
					}
			}
		}
		$scope.listOfNamesforAllDDLs = retList;
		$scope.listOfAllFieldNames = retListAllFieldNames;
		$scope.listOfAllCheckboxNames = retListAllChkboxNames;
		$scope.listOfAllRadioNames = retListAllRadioNames;
		$scope.listOfAllPasswordNames = retListAllPasswordNames;

	}

	
	$scope.callGetforDDLs = function (objName){
		ddlService.getDropDownListData(objName).success(function(data){
			$scope.ddlData = data;
			var theVal = objName;
			var objToPush ={};
			objToPush['type'] = theVal;
			objToPush['data'] = data;
//			objToPush [(theVal)] = data;
			$scope.listOfDataforAllDDLs.push(objToPush);
			$scope.sequenceList.push(theVal);
			
		}); 					

	}
	
	this.setCurrentForm = function(frmName){
		this.currentForm = frmName;
		$scope.crntFrm = frmName;
	}
	
	this.getCurrentForm = function(){
		return this.currentForm;
	}
	
	

	
	$scope.getDDLbyObjTypeName = function(typeName)
	{
		for(dtList in $scope.listOfDataforAllDDLs)
			{
				if(dtList.type==typeName)
					{
						return dtList.data;
					}
			}
		return null;
	}
	
	
	//Very important piece of code it binds an option list dynamically to the given object type received from the server
	//added track by because the data in the is dependent on ids-- 9th October 2016
	//Parameters : objType = the name of object type to be loaded in the drop down select 
	//		   	   visibleField = name of the filed to be shown in the DDL select 
	$scope.getOptionSelectPattern = function (objType,visibleField){
		
		return "x."+visibleField+" for x in listOfDataforAllDDLs[sequenceList.indexOf('"+objType+"')].data track by x.id";
	}
	
	$scope.getFieldPattern = function (key,objType)
	{
		return "AllFields[listOfAllFieldNames.indexOf('"+objType+"')].value";//+ key;
	}
	
	
	$scope.getBindingOption = function (optName){
		return "$scope.selected"+optName;
	}
	
	/*
	 * formToJson builds JSON String from form data
	 * */
	this.formToJson =function()
	{
		var strObjs ='{"'+this.currentForm+'":{';
		
		
		for(i = 0;i<$scope.listOfAllFieldNames.length;i++)
			{
				if($scope.AllFields[i])
					{
						strObjs+='"'+$scope.listOfAllFieldNames[i]+'":'+JSON.stringify($scope.AllFields[i]);
						
					}
				else
					{
						strObjs+='"'+$scope.listOfAllFieldNames[i]+'":null';
					}
				if(i<$scope.listOfAllFieldNames.length-1)
					{
						strObjs+=",";
					}
			}
		if($scope.selectedFields.length>0)
			{
				strObjs+=",";
			}
		//Append the DDL values to the JSON string
		for(i=0;i<$scope.selectedFields.length;i++)
			{
				strObjs+='"'+ $scope.listOfNamesforAllDDLs[i]+'":';
				if($scope.selectedFields[i])
				{
					strObjs+=JSON.stringify(angular.copy($scope.selectedFields[i]));
				}
				else
					{
					strObjs+="null";
					}
				if(i<$scope.selectedFields.length-1)
					{
					strObjs+=",";
					}
			}
		
		strObjs+="}}";
		this.jsonString = strObjs;
	}
	

	
	
	
	
	
	
	/*
	 * formToJson builds JSON String from form data
	 * */
	this.formToJson2 =function()
	{
		var strObjs ='{';
		
		
		for(i = 0;i<$scope.listOfAllFieldNames.length;i++)
			{
				if($scope.AllFields[i])
					{
						strObjs+='"'+$scope.listOfAllFieldNames[i]+'":'+JSON.stringify($scope.AllFields[i]);
						
					}
				else
					{
						strObjs+='"'+$scope.listOfAllFieldNames[i]+'":null';
					}
				if(i<$scope.listOfAllFieldNames.length-1)
					{
						strObjs+=",";
					}
			}
		if($scope.selectedFields.length>0)
			{
				strObjs+=",";
			}
		//Append the DDL values to the JSON string
		for(i=0;i<$scope.selectedFields.length;i++)
			{
				strObjs+='"'+ $scope.listOfNamesforAllDDLs[i]+'":';
				if($scope.selectedFields[i])
				{
					strObjs+=JSON.stringify(angular.copy($scope.selectedFields[i]));
				}
				else
					{
					strObjs+="null";
					}
				if(i<$scope.selectedFields.length-1)
					{
					strObjs+=",";
					}
			}
		if($scope.selectedRadios.length>0)
		{
			strObjs+=",";
		}//End Append the DDL values to the JSON string
		
		//Append the Radio Button values to the JSON string 
		for(i=0;i<$scope.selectedRadios.length;i++)
		{
			strObjs+='"'+ $scope.listOfAllRadioNames[i]+'":';
			if($scope.selectedRadios[i])
			{
				
				if($scope.getFieldType($scope.listOfAllRadioNames[i]) =='boolean')
					{
						if($scope.selectedRadios[i]=='Yes')
							{
								strObjs+=JSON.stringify(true);
							}
						else
							{
								strObjs+=JSON.stringify(false);
							}
					}
				else
					{
						strObjs+=JSON.stringify(angular.copy($scope.selectedRadios[i]));
					}
			}
			else
				{
				strObjs+="null";
				}
			if(i<$scope.selectedRadios.length-1)
				{
				strObjs+=",";
				}
		}
		
		
		
		if($scope.selectedPasswords1.length>0) //Append the Passwrod values to the JSON string 
		{
			strObjs+=",";
		}
		
		
		for(i=0;i<$scope.selectedPasswords1.length;i++)
		{
			strObjs+='"'+ $scope.listOfAllPasswordNames[i]+'":';
			if($scope.selectedPasswords1[i])
			{
				
				strObjs+=JSON.stringify(angular.copy($scope.selectedPasswords1[i]));
			}
			else
				{
				strObjs+="null";
				}
			if(i<$scope.selectedPasswords1.length-1)
				{
				strObjs+=",";
				}
		}


		
		
		
		strObjs+="}";
		this.jsonString = strObjs;
	}

	
	
	
	
	
	
	
	this.postFormData = function(url,reqType)
	{
		this.formToJson2();
//		var dataObj = this.jsonString;
		//var dataObj = {formData:encodeURIComponent(this.jsonString)};
		
		var dataObj = {"formData":this.jsonString,"ObjType":this.currentForm,"request_type":reqType};
		
		ddlService.postData(url,dataObj).success(function(data){
			$scope.strRequestResults = data;
		}); 					
	}
	$scope.setDDls = function (){
		var retVal = [];
		for(var i = 0; i<$scope.sequenceList.length;i++)
			{
				retVal.push($scope.datavalue[$scope.sequenceList[i]]);
//				$scope.selectedFields.push($scope.datavalue[$scope.sequenceList[i]]);
			}
		for(var i =0;i<retVal.length;i++)
			{
				$scope.selectedFields[i] = retVal[i];
			}
		
//		$scope.$apply();
	};
	$scope.setDDLsfromReceivedObject = function () {
		
		if(($scope.sequenceList.length<$scope.listOfNamesforAllDDLs.length) && ($scope.listOfNamesforAllDDLs.length>0))
		{
			$timeout(function () {
				$scope.setDDls();
			}, 500);
		}
	};
	$scope.setTextFieldsfromReceivedObject=function (){
		for(i=0;i<$scope.listOfAllFieldNames.length;i++)
		{
			$scope.AllFields[i] = $scope.datavalue[$scope.listOfAllFieldNames[i]];
		}	//$scope.AllFields[$scope.listOfAllFieldNames[e]].value = $scope.datavalue[$scope.listOfAllFieldNames[e]];
//			$scope.AllFields[$scope.listOfAllFieldNames.indexOf($scope.datavalue[)] = 
			
	}
	
	$scope.setRadiosfromReceivedObject = function(){
		for(i=0;i<$scope.listOfAllRadioNames.length;i++){
			var val = $scope.datavalue[$scope.listOfAllRadioNames[i]];
			if($scope.getFieldType($scope.listOfAllRadioNames[i])=='boolean')
			{
				if(val==true)
				{
					$scope.selectedRadios[i] = "Yes";//$scope.datavalue[$scope.listOfAllRadioNames[i]];
				}
				else 
				{
				$scope.selectedRadios[i] = "No";
				}
			}
			else {
				$scope.selectedRadios[i] = val;
			}
		}
	};
	
	$scope.setPasswordsForObject = function()
	{
		for(i=0;i<$scope.listOfAllPasswordNames.length;i++){
			$scope.selectedPasswords1[i] = "password";
			$scope.selectedPasswords2[i] = "password";
		}
	}
	this.loadObectForEdit = function (url,objDate){
		ddlService.getDataObject(url).success(function(data){
			$scope.datavalue = data;
			$scope.setPasswordsForObject();
			$scope.setTextFieldsfromReceivedObject();
			$scope.setRadiosfromReceivedObject();
			$scope.setDDLsfromReceivedObject();
		})
		};

	
	
		$scope.setField = function(){
			alert(JSON.stringify($scope.value1));
		};
		
		
	$scope.colModels = $window.colModels;//here we bind the global colModels column structure to $scope's colModels
										 //this is important because we are sharing this array of columns 
										 //with non-Angular JavaScript, that way we can manipulate the column structure 
										 //at one place. don't forget to include the .js file with colModels definitions
										 //before including this file... 
	$scope.init = function()//start loading of data for DDLs and metadata for all fields
	{
		$scope.loadDDLNames();//get unique names of select lists to be loaded 
		$scope.loadDDLData();//then load data for the select lists from the server 		
	}
	

}]);
