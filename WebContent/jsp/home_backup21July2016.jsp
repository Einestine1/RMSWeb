<%@page import="org.springframework.web.context.request.SessionScope"%>
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
<title>Property Management System</title>
<script type="text/javascript"  src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery-ui.min.js"></script>


<!-- <script type="text/javascript">
 $(document).ready(function(){
    if (jQuery) {  
     // jQuery is loaded  
    alert("Yeah!");
    } else {
    // jQuery is not loaded
    alert("Doesn't Work");
        }
    });
</script>
 -->
 <!-- 
 <script>
  $(function() {
    $( "#dialog" ).dialog();
    $( "#dialog" ).show();
  });
  </script>
 -->

<link rel="stylesheet" href="../css/jquery-ui.structure.min.css" type="text/css" />
<link rel="stylesheet" href="../css/jquery-ui.min.css" type="text/css" />
<link rel="stylesheet" href="../css/jquery-ui.theme.min.css" type="text/css" />
</head>
<body>
  
  <%
  
    	if (session != null) {
    		String loginName = (String) session.getAttribute("LoginName");
    		Boolean isLogedIn = false;
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
 <div id="dialog" title="Basic dialog" style="visibility: hidden;">
  <p>This is the default dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.</p>
</div>

	<frameset id="rowset" rows="80,*">
    <frame id="header" src="/bwise/app/header.jspx" name="fBWHeader" frameBorder="0" noresize scrolling="no" marginwidth="0" marginheight="0" />
    <!--[if IE]>
		<frameset id="colset" frameBorder="0" marginwidth="0" cols="246,*">
	<![endif]-->
	<!--[if !IE]>-->
		<frameset id="colset" frameborder="yes" border="3" cols="25%,*" rows="25%,*">
	<!--<![endif]-->
    	<!--[if IE]>
				<frameset id="leftFrame" frameBorder="0" rows="80%, 20%">
			<![endif]-->
			<!--[if !IE]>-->
                <frameset id="leftFrame" frameBorder="yes" border="1" rows="80%, 20%">
			<!--<![endif]-->
	    		<frame id="navtree" name="fBWTree" src="https://google.com.pk" frameBorder="0" marginwidth="0" marginheight="0" scrolling="no" />
	    		<frame id="navhist" name="fBWHistory" src="https://google.com.pk" frameBorder="0" marginwidth="0" marginheight="0" scrolling="no"/>
	    	</frameset>
	    	<frame id="main" name="fBWMain" src="https://google.com.pk" frameBorder="0" marginwidth="0" marginheight="0" scrolling="no"/>
	    	</frameset>
    </frameset>
</body>
</html>