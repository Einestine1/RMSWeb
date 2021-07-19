<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="pk.com.rsoft.rms.dal.ObjectTypeParser"%>
<%@page import="pk.com.rsoft.util.RsoftUtil"%>
<%@page import="javax.persistence.metamodel.IdentifiableType"%>
<%@page import="javax.persistence.metamodel.Attribute"%>
<%@page import="javax.persistence.metamodel.EntityType"%>
<%@page import="javax.persistence.Entity"%>
<%@page import="javax.persistence.metamodel.Metamodel"%>
<%@page import="java.lang.reflect.Constructor"%>
<%@page import="org.persistence.*"%>
<%@page import="java.net.Authenticator.RequestorType"%>
<%@page import="pk.com.rsoft.rms.dal.REQUEST_TYPE"%>
<%@page import="pk.com.rsoft.util.DBManager"%>
<%@page import="pk.com.rsoft.rms.dal.RequestTypeParser" %>
<%@page import="pk.com.rsoft.rms.dal.OBJECT_TYPE" %>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="RSoftRMSApp" ng-controller="MainController as appCtrl" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
body {
	background-image: url("../images/homelast.png");
}
</style>

<script type="text/javascript" src="../js/custom.js"></script>
<script type="text/javascript" src="../js/data-manager.js"></script>


<script type="text/javascript" src="../js/jquery/jquery.js"></script>
<script type="text/javascript" src="../js/jquery/jquery-ui.js"></script>


<script type="text/javascript" src="../js/angular/angular.js"></script>
<script type="text/javascript" src="../js/angular/services/ddl-services.js"></script>
<script type="text/javascript" src="../js/angular/app.js"></script>
<script type="text/javascript" src="../js/angular/angular-sanitize.js"></script>
<script type="text/javascript" src="../js/angular/controllers/main-controller.js"></script>
<script type="text/javascript" src="../js/angular/directives/main-directive.js"></script>


<title>Add/Update {{appCtrl.getCurrentForm()}}</title>
<link rel="stylesheet" href="../css/RMSApp.css" type="text/css" />

<link rel="styleSheet" href="../css/ui-grid.css"/>

<script type="text/javascript" src="../js/angular/ui-grid.js"></script>
<% 
	String strReqType =  request.getParameter("request_type");
	String strObjType = request.getParameter("ObjType");
	String strIDValue = request.getParameter("IdValue");
	
	Object obj=new Object();
   OBJECT_TYPE objType = OBJECT_TYPE.UNKNOWN;
   if(strObjType!=null)
   {   
	   objType = OBJECT_TYPE.parse(strObjType);
   }
   String url = "'../DomainObjectsService?ObjType="+strObjType +"&IdValue="+strIDValue+"&sngRec=true'";
   String strEditFunctionCall = "appCtrl.loadObectForEdit("+url+",null)";
   REQUEST_TYPE reqType = REQUEST_TYPE.UNKNOWN;
   if(strReqType!=null)
   {
   		reqType = RequestTypeParser.parse(strReqType);
   } 
   
   if(reqType!=REQUEST_TYPE.UPDATE)
   {
   		strEditFunctionCall = "";
   }
   
//   System.out.println(strEditFunctionCall);
   ///////objType.toString().toLowerCase()
   
   String objClsName = ObjectTypeParser.getClass(strObjType).getSimpleName().toLowerCase();
%>

<script type="text/javascript">
//var getObj = {ObjType:ObjType,IdValue:IdValue};
var url = <%=url%>;
//appCtrl.loadObectForEdit(<%=url %>,null);
</script>
</head>
<!-- Here in the JSP script-let we set the form/object to be displayed -->
<body onload="$('input:text:visible:first').focus()" ng-init="appCtrl.setCurrentForm('<%=objClsName%>');<%=strEditFunctionCall %>;init();">

		<div class="formtitlecell" style="width: 354px;" align="Center">Add/Update {{appCtrl.getCurrentForm()}}</div>
<p></p>
	<form method="POST" ng-submit="createUser()" id="frm1">

				<div class="tbl" style="font-size: 12"><!-- Start of table for dynamic form -->
				<div ng-repeat="elmnt in getCurrentFormMeta()" ng-if="elmnt.showOnForm == true">
					<div class="tblrow" ng-if="elmnt.inputType != 'password' ">
						<div class="tblcell" style="width: 150px; ">{{elmnt.title}} :</div>
						<div class="tblcell" ng-if="elmnt.inputType == 'text'"><input type="text" id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 200px; " ng-model="AllFields[listOfAllFieldNames.indexOf(elmnt.objType)]" /></div>
						{{elmnt.objType1}}
						<div class="tblcell" ng-if="elmnt.inputType == 'select'">
							<select id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 206px; " ng-model="selectedFields[sequenceList.indexOf(elmnt.objType)]"  ng-options="{{getOptionSelectPattern(elmnt.objType,elmnt.visibleField)}}" ></select>
						</div>								
						<div class="tblcell" ng-if="elmnt.inputType =='radio' && elmnt.dataType=='boolean'">
							<input type="radio" id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 50px; " ng-model="selectedRadios[listOfAllRadioNames.indexOf(elmnt.objType)]" value="Yes" />Yes
							<input type="radio" id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 50px; " ng-model="selectedRadios[listOfAllRadioNames.indexOf(elmnt.objType)]" value="No" />No
					</div>
					</div>
					
					<div class="tblrow" ng-if="elmnt.showOnForm == true && elmnt.inputType=='password'">
						<div class="tblcell" style="width: 150px; ">{{elmnt.title}} :</div>
						<div class="tblcell"><input type="password" id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 200px; " ng-model="selectedPasswords1[listOfAllPasswordNames.indexOf(elmnt.objType)]" />
						</div>							
					</div>
					
					
					<div class="tblrow" ng-if="elmnt.showOnForm == true && elmnt.inputType=='password'">
						<div class="tblcell" style="width: 150px; ">Retype {{elmnt.title}} :</div>
						<div class="tblcell"><input type="password" id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 200px; " ng-model="selectedPasswords2[listOfAllPasswordNames.indexOf(elmnt.objType)]" />
						</div>							
					</div>
					
					
					
					</div>
					
					<p></p>

				<div class="tbl" style="font-size: 12"><!-- Start of table for dynamic form -->				
					<div class="tblrow">
						<div class="tblcell"></div>
						<div class="tblcell"><input src="../images/icons/save1.png" type="image"  value="Save" id="Save" alt="Save" title="Save" ng-click="appCtrl.postFormData('../DomainObjectsManager','<%=strReqType %>');" />
						<input type="image" src="../images/icons/close.png" value="Close" id="Cancel" onclick="closeWindow(window);" alt="Close" title="Close" />
						</div>		
					</div>
				</div>

				</div><!-- End of table for dynamic form -->	

		<input type="hidden" id="hidden_request_type" name="hidden_request_type" value="<%=strReqType%>" />
		</form>
<div ng-show="strRequestResults.length>1" ng-bind-html="strRequestResults"></div>
<!-- <br> -->
<!-- JSON-->{{appCtrl.jsonString}} -->
<!-- <br> -->
<!-- {{selectedPasswords1|json}} -->
<!-- <br> -->
<!-- {{selectedPasswords2|json}} -->
</body>
</html>