<%@page import="org.persistence.User"%>
<%@page import="java.net.Authenticator.RequestorType"%>
<%@page import="pk.com.rsoft.rms.dal.REQUEST_TYPE"%>
<%@page import="pk.com.rsoft.util.DBManager"%>
<%@page import="pk.com.rsoft.rms.dal.RequestTypeParser" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
body {
	background-image: url("../images/homelast.png");
}
</style>

<script src="../js/jquery.js"></script>
<script src="../js/custom.js"></script>
<title>Add/Update User</title>
<link rel="stylesheet" href="../css/RMSApp.css" type="text/css" />
</head>
<body onload="$('input:text:visible:first').focus();">

<%

String strReqType =  request.getParameter("request_type");

User user = new User();
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
		<div class="formtitlecell" style="width: 354px;" align="Center">Add/Update User</div>
<p></p>
	<form action="" method="POST">

				<div class="tbl" style="font-size: 12">
					<div class="tblrow">
						<div class="tblcell">User Name</div>
						<div class="tblcell"><input type="text" id="userName" name="userName" style="width: 200px; " value="<%=user.getUserName()%>" <%=readOnly%> ></input></div>		
					</div>
					<p></p>
					<div class="tblrow">
						
						<div class="tblcell" style="width: 235px;font-size: 12">Password</div><div class="tblcell"><input type="password" id="password1" name="password1" style="width: 200px; "></input></div>		
					</div>
					<p></p>
					<div class="tblrow">
						<div class="tblcell">Confirm Password</div>
						<div class="tblcell" style="width: 172px; "><input type="password" id="password2" name="password2" style="width: 200px; "></input></div>		
					</div>
					<p></p>
					<div class="tblrow">
						<div class="tblcell">First Name</div>
						<div class="tblcell" style="width: 172px; "><input type="text" id="firstname" name="firstname" style="width: 200px; " value="<%=user.getFirstName()%>" ></input></div>		
					</div>
					<p></p>
					<div class="tblrow">
						<div class="tblcell">Last Name</div>
						<div class="tblcell" style="width: 172px; "><input type="text" id="lastname" name="lastname" style="width: 200px; " value="<%=user.getLastName()%>" ></input></div>		
					</div>
					<p></p>
					<div class="tblrow">
						<div class="tblcell">Email</div>
						<div class="tblcell" style="width: 172px; "><input type="text" id="email" name="email" style="width: 200px; " value="<%=user.getEmail()%>" ></input></div>		
					</div>
					<p></p>
					<div class="tblrow">
						<div class="tblcell">Is Admin</div>
						<div class="tblcell" style="width: 172px; "> 
						 <input type="radio" name="isAdmin" id="isAdmin" value="Yes" checked="<%=strCheckedYes %>" > Yes
						 <input type="radio" name="isAdmin" id="isAdmin" value="No" checked="<%=strCheckedNo%>"> No<br></div>		
					</div>
					<p></p>
					<div class="tblrow">
						<div class="tblcell"></div>
						<div class="tblcell"><input src="../images/icons/save1.png" type="image"  value="Save" id="Save" onclick="createUser();//closeWindow(window);" alt="Save" title="Save"></input>
						<input type="image" src="../images/icons/close.png" value="Close" id="Cancel" onclick="closeWindow(window);" alt="Close" title="Close"></input>
						</div>		
					</div>
				</div>
		<input type="hidden" id="hidden_request_type" name="hidden_request_type" value="<%=strReqType%>"></input>
		</form>

</body>
</html>