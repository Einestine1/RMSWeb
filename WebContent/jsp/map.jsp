<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="../css/leaflet/leaflet.css" />
<link rel="stylesheet" href="../css/RMSApp.css" />
<script src="../js/jquery/jquery.js"></script>
<script src="../js/gis/leaflet.js"></script> <!-- or use leaflet-src.js -->
<title>Map</title>
</head>
<body>

<script type="text/javascript">
$(function(){
	//var mymap = L.map('mapid').setView([51.505, -0.09], 13);
	var mymap = L.map('mapid').setView([32.4972, 74.5361], 13);
	
	var marker = L.marker([32.4972, 74.5361]).addTo(mymap);
	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
	    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="http://mapbox.com">Mapbox</a>',
	    maxZoom: 18,
	    id: 'mapbox.streets',
	    accessToken: 'pk.eyJ1IjoiZWluZXN0aW5lMSIsImEiOiJjaXVtb3A1enMwMDBuMnRsODhhOGRyMG1tIn0.8_p0vC-G912ofDlsik5QSg'
	}).addTo(mymap);
	
/*	
	var circle = L.circle([51.508, -0.11], {
	    color: 'red',
	    fillColor: '#f03',
	    fillOpacity: 0.5,
	    radius: 500
	}).addTo(mymap);
	
	
	var polygon = L.polygon([
	                         [51.509, -0.08],
	                         [51.503, -0.06],
	                         [51.51, -0.047]
	                     ]).addTo(mymap);
	
	
	
	marker.bindPopup("<b>Hello world!</b><br>I am a popup.").openPopup();
	circle.bindPopup("I am a circle.");
	polygon.bindPopup("I am a polygon.");
	
	
	
	var popup = L.popup()
    .setLatLng([51.5, -0.09])
    .setContent("I am a standalone popup.")
    .openOn(mymap);
	*/
});

</script>


<div id="mapid" style="width: 600px; height: 400px;"></div>
</body>
</html>