<%@page import="pk.com.rsoft.rms.dal.OBJECT_TYPE"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="RSoftRMSApp" ng-controller="MainController as appCtrl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<!--jQuery dependencies-->
	<link rel="stylesheet" href="../css/jquery-ui.css" />    
 	<link rel="stylesheet" href="../css/jquery-ui.theme.css" />      
	<script src="../js/jquery/jquery.js"></script>
	<script src="../js/jquery/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../js/custom.js"></script>
	<script src="../js/data-manager.js"></script>
	
<!--PQ Grid files-->
    <link rel="stylesheet" href="../css/pqgrid.min.css" />
    <script src="../js/pqgrid.min.js"></script>
<!--PQ Grid Office theme-->
    <link rel="stylesheet" href="../js/themes/Office/pqgrid.css" />

<!-- AngularJS dependencies -->
<script type="text/javascript" src="../js/angular/angular.js"></script>
<script type="text/javascript" src="../js/angular/angular-sanitize.js"></script>
<script type="text/javascript" src="../js/angular/services/ddl-services.js"></script>
<script type="text/javascript" src="../js/angular/app.js"></script>
<script type="text/javascript" src="../js/angular/controllers/main-controller.js"></script>

<style type="text/css">
body {
	background-image: url("../images/homelast.png");
}

</style>
<% String strObjType = request.getParameter("ObjType");
   OBJECT_TYPE objType = OBJECT_TYPE.UNKNOWN;
   if(strObjType!=null)
   {
	   objType = OBJECT_TYPE.parse(strObjType);
   }
%>
<script type="text/javascript">
$(function(){
<%-- //$.ajax({url: '../DomainObjectsService?ObjType=<%=strObjType.toString().toLowerCase()%>',success: function(result){ --%>
<%-- 	//InitGrid(result,"<%=strObjType%>s Information",'<%=strObjType/*.toString().toLowerCase()*/%>',window); --%>
// }});

<%--  //var url = '../DomainObjectsService?ObjType=<%=strObjType.toString().toLowerCase()%>'; --%>
var url = '../DomainObjectsServiceWithWrappedMeta?ObjType=<%=strObjType.toString().toLowerCase()%>';

LoadGridData(url,"<%=strObjType%>s Information",'<%=strObjType/*.toString().toLowerCase()*/%>',window);


});
</script>
<title><%=objType.toString().toLowerCase()%>s</title>

<script type="text/javascript" src="../js/angular/ui-grid.js"></script>
<link rel="../css/app.css"></link>
<link rel="../css/RMSApp.css"></link>
</head>
<body ng-init="appCtrl.setCurrentForm('<%=objType.toString().toLowerCase()%>');">


<div id="grid_crud" style="margin:auto;"></div>

<div id="jsGrid" style="margin:5px auto;"></div>



<div id="popup-dialog-crud" style="display:none;">
		<form id="crud-form">
					<div class="tblrow" ng-repeat="elmnt in appCtrl.getCurrentFormMeta()" ng-if="elmnt.hidden != true">
						<div class="tblcell" style=" float: left;">{{elmnt.title}} :</div>
						<div class="tblcell" style=" float: right;" ng-if="elmnt.inputType == 'text'"><input type="text" id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 200px; " ></input></div>
						
						<div class="tblcell" style=" float: right;" ng-if="elmnt.inputType == 'select'">
							<select id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 206px; " ng-model="selectedFields[sequenceList.indexOf(elmnt.objType)]"  ng-options="{{getOptionSelectPattern(elmnt.objType,elmnt.visibleField)}}"></select>
						</div>								
					
					
					</div>
					<p></p>
		</form>
	</div>

</body>
</html>