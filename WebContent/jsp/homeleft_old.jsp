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

<!--jQuery dependencies-->
	<link rel="stylesheet" href="../css/jquery-ui.css" />    
	
	<script src="../js/jquery/jquery.js"></script>
	<script src="../js/jquery/jquery-ui.min.js"></script>
	<script src="../js/data-manager.js"></script> 

<script type="text/javascript" src="../js/CollapsibleLists.js"></script>
<link rel="stylesheet" href="../css/collapseableliststyles.css" />    

<title>RMS Menu</title>
</head>
<body>
      <ul class="treeView">
        <li>
          Property Management
          <ul class="collapsibleList">
            <li>
              Plots/Files
              <ul>
                <li>
                  Plots
                  <ul>
                    <li>apply()</li>
                    <li>applyTo(node)</li>
                  </ul>
                </li>
                <li>
                  Files
                  <ul>
                    <li>Expanding/opening</li>
                    <li>Collapsing/closing</li>
                  </ul>
                </li>
              </ul>
            </li>
            <li>
              Uses
              <ul>
                <li>Directory listings</li>
                <li>Tree views</li>
                <li>Outline views</li>
              </ul>
            </li>
          </ul>
        </li>
      </ul>


<script type="text/javascript">
	CollapsibleLists.apply();
</script>
  
</body>
</html>