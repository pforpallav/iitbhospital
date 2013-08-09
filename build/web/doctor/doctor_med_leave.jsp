<html>
<title>IITB Hospital - New Medical Leave</title>
<link rel="stylesheet" type="text/css" href="Aristo.css">
<link rel="stylesheet" type="text/css" href="style.css">
<!--link href='http://fonts.googleapis.com/css?family=Ubuntu:400,700' rel='stylesheet' type='text/css'-->

<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript">
	onload = function() {
					$(".login-screen").css("margin-top", ($(window).height()/2 - 200) + 'px');
					$(".menu-screen").css("margin-top", ($(window).height()/2 - 200) + 'px');
					$(".screen").css("margin-top", ($(window).height() - $(".screen").height())/2 + 'px');
					
					//alert(document.documentElement.clientHeight + 'px');
				};
		
	onresize = function() {
					$(".login-screen", "#menu-screen").css("margin-top", ($(window).height()/2 - 200) + 'px');
					$(".menu-screen").css("margin-top", ($(window).height()/2 - 200) + 'px');
					$(".screen").css("margin-top", ($(window).height() - $(".screen").height())/2 + 'px');
					
					//alert(document.documentElement.clientHeight + 'px');
				};

    
				
	$(function(){
        
        
		$("#login-button").button();
        $("#submit").button();
        $("#back-button").button({
                    icons: {
                        primary: 'ui-icon-arrowreturnthick-1-w'
                    }
                });
        $("#logout-button").button({
                    icons: {
                        secondary: 'ui-icon-transferthick-e-w'
                    }
                });
        $("#submit-book-app").button();
        $("#date_from").datepicker({
            altFormat: "D",
            altField: "#dayOfDate",
            dateFormat: "yy/mm/dd"
        });
        
        $("#date_to").datepicker({
            altFormat: "D",
            altField: "#dayOfDate",
            dateFormat: "yy/mm/dd"
        });
        
	});
        
        validatePatientId = function()
        {
            var dataString = "patient_id="+ $('#patient_id').val();
            var result;
            $.ajax({
                        async:false,
                        type: "GET",
                        url: "../doctorGetPatientName",
                        data: dataString,
                        cache: false,
                        success: function(html)
                        {
                            //alert(html);
                            result=html;
                        } 
                    });

             if(result == "")
                 {
                     $('#patient_id').val("");
                     $('#submit').button("disable");
                     $('#welcome-text').text("New Medical Leave");
                 }
             else
                 {
                     $('#welcome-text').text("Medical Leave For "+result);
                     $('#patient-name').val(result);
                     $('#submit').button("enable");
                 }

        };
    

</script>

<body>
	<div class="screen">
        <div class="back-button-div"><a id="back-button" href="menu.jsp">Back</a></div>
            <div class="logout-button-div"><a href="../doctorLogout" id="logout-button">Logout</a></div>
		<div class="title">
        	Write a Medical-Leave
        </div>
        <div class="screen-div" style="max-height: 700px">
            <div class="screen-div-title" id="welcome-text">New Medical Leave</div>
            <form action="../doctorMedicalLeave" method="post" class="ui-form" style="text-align: center;">

            <input type="hidden" value="" id="patient-name" name="patient-name">

            <div class="label-set">
                <label for="patient_id">Patient ID: </label>
                <input type="text" name="patient_id" id="patient_id" onchange="validatePatientId()"></br>
            </div>

            <div class="label-set">
                <label for="date_from">Date (From): </label>
                <input type="text" name="date_from" id="date_from"></br>
            </div>

            <div class="label-set">
                <label for="date_to">Date (To): </label>
                <input type="text" name="date_to" id="date_to"></br>
            </div>

            <div class="label-set" style="height: 100px">
                <label for="notes">Notes: </label>
                <textarea name="notes" id="notes" style="resize:none; height:100px; width: 200px "></textarea> 
            </div>

            <input type="submit" value="Confirm"  id="submit" style="margin-top:20px" disabled>

            </form>
        </div>				
	</div>
</body>
</html>
