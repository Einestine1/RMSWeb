<%@page import="org.springframework.web.context.request.SessionScope"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>

<link rel="icon" type="image/png" href="../images/icons/aapicons/app-icon-16x16.png" />
<base target="center">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Property Management System</title>

<link rel="stylesheet" href="../css/RMSApp.css" type="text/css" />

</head>

<%
  String loginName ="";
  Boolean isLogedIn = false;
    	if (session != null) {
    		loginName = (String) session.getAttribute("LoginName");
    		if(session.getAttribute("logedin")!=null)
    		{
    			isLogedIn = (Boolean) session.getAttribute("logedin");
    		}
    		if (!isLogedIn) {
    			response.sendRedirect("../index.jsp");
    		}

    	}
    %>

<frameset id="mainFrame" rows="20%,*" cols="*" border="1" framespacing="1">
	<frame src="hometop.jsp" noresize="noresize" frameborder="1" scrolling="no" ></frame>
	<frameset id="internalFrame" rows="*" cols="20%,*" border="1" framespacing="1">
		<frame id="left" src="homeleft.jsp" noresize="noresize" frameborder="1" scrolling="no"></frame>
		<frame id="center" name="center" src="homecenter.jsp" noresize="noresize" frameborder="0" scrolling="auto"></frame>
	</frameset>
</frameset>
</html>