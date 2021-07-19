<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
body {
	background-image: url("../images/home_left.png");
	background-repeat: no-repeat;
	background-size:cover;
}
</style>

<link rel="styleSheet" href="../css/treemenu.css"></link>


<script src="../js/TreeMenu.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/jquery/jquery.js"></script>
<script type="text/javascript" src="../js/jquery/jquery-ui.js"></script>



<script type="text/javascript" src="../js/custom.js"></script>
<script type="text/javascript" src="../js/data-manager.js"></script>

<script type="text/javascript" src="../js/angular/angular.js"></script>
<script type="text/javascript" src="../js/angular/app.js"></script>
<script type="text/javascript" src="../js/angular/controllers/main-controller.js"></script>

<title>RMS Menu</title>

<base target="center">

</head>
<body>
  <div class="menu" style="border: none;float: left;">
		<ul id="RMSMenu">
			<li>Main
				<ul>
					<li><a href="mainpage.jsp?ObjType=Plot">Plots</a></li>
					<li><a href="mainpage.jsp?ObjType=PlotSize">Plot Sizes</a></li>
					<li><a href="mainpage.jsp?ObjType=PlotCategory">Plot Categories</a></li>
					<li><a href="mainpage.jsp?ObjType=PlotStatus">Plot Statuses</a></li>
					<li><a href="mainpage.jsp?ObjType=Member">Members</a></li>
					<li><a href="mainpage.jsp?ObjType=PaymentSchedule">Payment Schedule</a></li>
					<li><a href="mainpage.jsp?ObjType=Payment">Payments</a></li>
					
				</ul>
			</li>

			<li>Administration
				<ul>
					<li><a href="mainpage.jsp?ObjType=User">Users</a></li>
					<li><a href="javascript:void(0)">Configuration</a></li>
					<li><a href="javascript:void(0)">Rights</a></li>
				</ul>
			</li>
		</ul>



		<script type="text/javascript">make_tree_menu('RMSMenu');</script></div>

</body>
</html>