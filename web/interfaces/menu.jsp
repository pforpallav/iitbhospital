<html>
<title>IITB Hospital - Menu</title>
<link rel="stylesheet" type="text/css" href="Aristo.css">
<link rel="stylesheet" type="text/css" href="style.css">
<!--link href='http://fonts.googleapis.com/css?family=Ubuntu:400,700' rel='stylesheet' type='text/css'-->

<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript">
	onload = function() {
					$(".login-screen").css("margin-top", ($(window).height()/2 - 200) + 'px');
					$(".menu-screen").css("margin-top", ($(window).height()/2 - 100) + 'px');
					
					//alert(document.documentElement.clientHeight + 'px');
				};
		
	onresize = function() {
					$(".login-screen", "#menu-screen").css("margin-top", ($(window).height()/2 - 200) + 'px');
					$(".menu-screen").css("margin-top", ($(window).height()/2 - 100) + 'px');
					
					//alert(document.documentElement.clientHeight + 'px');
				};
				
	$(function(){			
		$("#app_but").button();
		$("#pres_but").button();
		$("#med_but").button();
		$("#logout-button").button({
                    icons: {
                        secondary: 'ui-icon-transferthick-e-w'
                    }
                });
	});

</script>

<body>
	<div class="menu-screen" style="height: 200px; width: 600px; position: relative;">
            <div class="logout-button-div" style="right: 11px; position: absolute;"><a href="../logout"><button id="logout-button">Logout</button></a></div>
            <a href="../appointments" id="app_but" style="margin-top: 64px; margin-left: 27px;"><div class="big-button"><img src="images/app.png"></br>APPOINTMENTS</div></a>
            <a href="../prescriptions"  id="pres_but" style="margin-top: 64px; margin-left: 27px;"><div class="big-button"><img src="images/pres.svg" height="35"></br>PRESCRIPTIONS</div></a>
            <a href="../medical_leaves"  id="med_but" style="margin-top: 64px; margin-left: 27px;"><div class="big-button"><img src="images/med.png"></br>MEDICAL LEAVES</div></a>
	</div>
</body>
</html>
