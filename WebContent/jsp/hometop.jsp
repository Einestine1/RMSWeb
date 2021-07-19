<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
body {
	background-image: url("../images/home_top.png");
	background-repeat: no-repeat;
	background-size:cover;
}
</style>

<style>
  #loginInfo {
    position: fixed;
    bottom: 0;
    right: 0;
  }
</style>

<title>Insert title here</title>
</head>
<body>

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
    			System.out.println("Not loged in! " + loginName );
    			response.sendRedirect("../index.jsp");
    		}

    	}
%>
    
<div style="widows:50; height: 100;"> <img  src="../images/icons/aapicons/app-icon.png" style="width: 175px; height: 101px; opacity:0.7;filter:alpha(opacity=70);"></img ></div>    

<div id="loginInfo">
	<font style="color: white;size: 8px;">Welcome <%=loginName %> 
 		<a href="../doLogin?request_type=Logout" target="_top">Logout?</a>&nbsp;&nbsp;
 	</font>
</div>

</body>
</html>