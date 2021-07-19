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


<title>Add/Update {{appCtrl.getCurrentForm()}}</title>
<link rel="stylesheet" href="../css/RMSApp.css" type="text/css" />

<link rel="styleSheet" href="../css/ui-grid.css"/>

<script type="text/javascript" src="../js/angular/ui-grid.js"></script>
<% String strObjType = request.getParameter("ObjType");
   OBJECT_TYPE objType = OBJECT_TYPE.UNKNOWN;
   if(strObjType!=null)
   {
   
   	   DBManager dbmgr = new DBManager();
	   objType = OBJECT_TYPE.parse(strObjType);
	   System.out.println(objType);
	   Constructor<?> construct = ObjectTypeParser.getClass(strObjType).getConstructor();
	   Object obj = construct.newInstance();
	   System.out.println("Hello"+obj.getClass());	 
	   Object id = dbmgr.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj);  
	   System.out.println("ID is : " +id.toString());
	   
	   Metamodel model = dbmgr.getEntityManager().getMetamodel();
	   
	   for(EntityType<?> e : model.getEntities())
	   {
	   		System.out.println(e.getName()+":-");
	   		for(Attribute<?,?> atr:e.getAttributes())
	   		{
	   			System.out.print(atr.getName()+ " ,");
	   			

	   		}
	   		System.out.println();
	   		System.out.println("Key Column of "+e.getName()+" is "+RsoftUtil.getPKColumnName(e.getJavaType()));
	   			
	   }
   }
%>
</head>
<!-- Here in the JSP script-let we set the form/object to be displayed -->
<body onload="$('input:text:visible:first').focus();" ng-init="appCtrl.setCurrentForm('<%=objType.toString().toLowerCase()%>');init();">

<%

String strReqType =  request.getParameter("request_type");

User user = new User();
System.out.println("Key Column of "+user+" is "+RsoftUtil.getPKColumnName(user.getClass()));
String readOnly = "";
if(strReqType!=null)
{
	REQUEST_TYPE requestType = RequestTypeParser.parse(strReqType);

	if(requestType==REQUEST_TYPE.UPDATE)
	{
		readOnly="readonly";
		String userId = request.getParameter("userId");
		if(userId!=null)
		{
			DBManager dbmgr = new DBManager();
			user = (User) dbmgr.findById(user, "id", Long.parseLong(userId));		
			System.out.println(dbmgr.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(user).toString());
		}
	}
	else
	{
		user = null;
	}
 }
 else
 {
 	strReqType = "Add";
 }
 String strCheckedYes="checked";
 String strCheckedNo="unchecked";
 if(user.getIsAdmin())
 {
 	strCheckedYes = "checked";
 	strCheckedNo = "unchecked";
 }
 else
 {
 	strCheckedYes = "unchecked";
 	strCheckedNo = "checked"; 
 }
 %>
		<div class="formtitlecell" style="width: 354px;" align="Center">Add/Update {{appCtrl.getCurrentForm()}}</div>
<p></p>
	<form method="POST" ng-submit="createUser()" id="frm1">

				<div class="tbl" style="font-size: 12">
					<div class="tblrow" ng-repeat="elmnt in getCurrentFormMeta()" ng-if="elmnt.hidden != true">
						<div class="tblcell">{{elmnt.title}} :</div>
						<div class="tblcell" ng-if="elmnt.inputType == 'text'"><input type="text" id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 200px; " value="<%=user.getUserName()%>" <%=readOnly%> ng-model="AllFields[listOfAllFieldNames.indexOf(elmnt.objType)]"></input></div>
						{{elmnt.objType1}}
						<div class="tblcell" ng-if="elmnt.inputType == 'select'">
							<select id="{{elmnt.title}}" name="{{elmnt.title}}" style="width: 206px; " <%=readOnly%> ng-model="selectedFields[sequenceList.indexOf(elmnt.objType)]"  ng-options="{{getOptionSelectPattern(elmnt.objType,elmnt.visibleField)}}"></select>
						</div>								
					
					<p><p>
					
					</div>
					<p></p>
					<div class="tblrow">
						<div class="tblcell"></div>
						<div class="tblcell"><input src="../images/icons/save1.png" type="image"  value="Save" id="Save" alt="Save" title="Save" ng-click="appCtrl.postFormData('../DomainObjectsManager');"></input>
						<input type="image" src="../images/icons/close.png" value="Close" id="Cancel" onclick="closeWindow(window);" alt="Close" title="Close"></input>
						</div>		
					</div>
				</div>
		<input type="hidden" id="hidden_request_type" name="hidden_request_type" value="<%=strReqType%>"></input>
		</form>
		<br>
JSON string :{{appCtrl.jsonString}}<br>

<div ng-bind-html="strRequestResults"></div>
</body>
</html>