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
$.ajax({url: '../DomainObjectsService?ObjType=<%=objType.toString().toLowerCase()%>',success: function(result){
	load(result,"<%=strObjType%>s Information",'<%=objType.toString().toLowerCase()%>s',window);
}});
});
</script>
<title>Plots</title>

<script type="text/javascript" src="../js/angular/ui-grid.js"></script>
<link rel="../css/app.css"></link>
<link rel="../css/RMSApp.css"></link>
</head>
<body ng-init="appCtrl.setCurrentForm('<%=objType.toString().toLowerCase()%>s');">
<!-- <body onload="buildpmGrid('jsGrid','../getPlots?request_type=fetch');"> -->


<div id="grid_crud" style="margin:auto;"></div>

<div id="jsGrid"></div>



<div id="popup-dialog-crud" style="display:none;">
		<form id="crud-form">
			<table>
				<tbody>
					<tr ng-repeat="elmnt in appCtrl.getCurrentFormMeta()">
						<td class="title" ng-hide="elmnt.hidden">{{elmnt.title}}:</td>
						<td ng-hide="elmnt.hidden"><input type="text" name="{{elmnt.title}}"></td>
					</tr>
				</tbody>
			</table>
<!-- 			<div class="formtitle"> -->
<!-- 					<div class="formtitlerow" data-ng-repeat="elmnt in appCtrl.getCurrentFormMeta()"> -->
<!-- 						<div class="formtitlecell">{{elmnt.title}}:</div> -->
<!-- 						<div class="formtitlecell"><input type="text" name="{{elmnt.title}}"></div> -->
<!-- 					</div> -->
<!-- 				</div> -->
		</form>
	</div>

<!-- <div>Current selected form is : {{appCtrl.getCurrentForm()}}</div> -->
<!-- <div>Current selected form's meta data is : {{appCtrl.getCurrentFormMeta()}}</div> -->
</body>
</html>