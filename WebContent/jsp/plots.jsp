<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp" ng-controller="MainController as appCtrl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<!--jQuery dependencies-->
	<link rel="stylesheet" href="../css/jquery-ui.css" />    
 	<link rel="stylesheet" href="../css/jquery-ui.theme.css" />      
	<script src="../js/jquery/jquery.js"></script>
	<script src="../js/jquery/jquery-ui.min.js"></script>
	<script src="../js/data-manager.js"></script>
<!--PQ Grid files-->
    <link rel="stylesheet" href="../css/pqgrid.min.css" />
    <script src="../js/pqgrid.min.js"></script>
<!--PQ Grid Office theme-->
    <link rel="stylesheet" href="../js/themes/Office/pqgrid.css" />


<style type="text/css">
body {
	background-image: url("../images/homelast.png");
}

</style>
<script type="text/javascript">
$(function(){
$.ajax({url: '../getPlots?request_type=fetch',success: function(result){
	load(result,"Plots Information",'plots');
}});
});
</script>
<title>Plots</title>
<style type="text/css">
    .ui-dialog *
    {
        font-family:Tahoma;
        font-size:11px;
    }
    .ui-dialog form#crud-form
    {
        margin-top:20px;
    }
    .ui-dialog form#crud-form input
    {
        width:230px;
        overflow:visible;/*fix for IE*/
    }
    .ui-dialog form#crud-form td.label
    {
        font-weight:bold;padding-right:5px;
    }
    div.pq-grid-toolbar-crud
    {
        text-align:center;
    }
</style>
<script type="text/javascript" src="../js/angular/angular.js"></script>
<script type="text/javascript" src="../js/angular/app.js"></script>
<script type="text/javascript" src="../js/angular/ui-grid.js"></script>
<link rel="../css/app.css"></link>
</head>
<body>
<!-- <body onload="buildpmGrid('jsGrid','../getPlots?request_type=fetch');"> -->


<div id="grid_crud" style="margin:auto;"></div>

<div id="jsGrid"></div>



<div id="popup-dialog-crud" style="display:none;">
<form id="crud-form">
    <table align="center">
    <tbody><tr>
    <td class="label">Company Name:</td>
    <td><input type="text" name="company"></td>
    </tr>
    <tr>
    <td class="label">Symbol:</td>
    <td><input type="text" name="symbol"></td>
    </tr>
    <tr>
    <td class="label">Price:</td>
    <td><input type="text" name="price"></td>
    </tr>
    <tr>
    <td class="label">Change:</td>
    <td><input type="text" name="change"></td>
    </tr>
    <tr>
    <td class="label">%Change:</td>
    <td><input type="text" name="pchange"></td>
    </tr>
    <tr>
    <td class="label">Volume:</td>
    <td><input type="text" name="volume"></td>
    </tr>
    </tbody></table>
</form>
</div>
<!-- <show-counter></show-counter> -->



</body>
</html>