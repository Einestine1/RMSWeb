<!DOCTYPE html>
<html>
<head>
<style type="text/css">
body {
	background-image: url("images/homelast.png");
}
</style>

<link rel="icon" type="image/png" href="images/icons/aapicons/app-icon-16x16.png" />


<script src="js/jquery/jquery.js"></script>
<script src="js/custom.js"></script>


<title>Property Management System</title>

<link rel="stylesheet" href="css/RMSApp.css" type="text/css" />
</head>
<body onload="$('input:text:visible:first').focus();">

	<div class="center-div">
		<div class="tableTitle" align="center">
			<div class="titlerow">
				<div class="titlecell">
					<h2 class="home-title" align="center">Property Management
						System</h2>
				</div>
			</div>
		</div>

		<div align="center">
			<form action="jsp/home.jsp" method="post" onsubmit="return false;">
				<div class="tableLogin" align="Center">
					<div class="loginrow">
						<div class="logincell" align="left">Login*:</div>
						<div class="logincell">
							<input type="text" id="Login" name="Login" style="width: 200px">
						</div>
					</div>

					<div class="loginrow">
						<div class="logincell" align="left">Password*:</div>
						<div class="logincell" align="left">
							<input type="password" style="width: 200px" id="password" name="password">
						</div>
					</div>
					<div class="loginrow">
						<div class="logincell"></div>
						<div class="logincell">
							<div class="tableLogin" align="left">
								<div class="loginrow" align="left">
									<div class="logincell" align="left">
										<input type="submit" value="Ok" style="width: 80px" onclick=" return loginUser();">
									</div>
									<div class="logincell" align="left">
										<input type="submit" value="Cancel" style="width: 80px">
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		<div id="div1" style="font: serif;"> </div>
		<input type="image" src="images/icons/Add.png" onclick="openPopup('jsp/addupdateobject.jsp?ObjType=user',true,500,350); return false;" style="width: 19px; height: 16px;">User
		</div>
	
	</div>
</body>
</html>