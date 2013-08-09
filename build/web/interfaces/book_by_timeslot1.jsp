<%@page import="java.sql.ResultSet"%>
<html>
     <% 
                    
                   // ResultSet timeslots=(ResultSet)request.getAttribute("timeslots");
                    String department =(String)request.getAttribute("department");
                   // String department=(String)request.getAttribute("department");
                            
                %>
<title>IITB Hospital - Book Appointment</title>
<link rel="stylesheet" type="text/css" href="interfaces/Aristo.css">
<link rel="stylesheet" type="text/css" href="interfaces/style.css">
<!--link href='http://fonts.googleapis.com/css?family=Ubuntu:400,700' rel='stylesheet' type='text/css'-->

<script type="text/javascript" src="interfaces/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="interfaces/js/jquery-ui.min.js"></script>
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
        $(".confirm").dialog({
            autoOpen: false,
            draggable: false,
            modal: true,
            resizable: false,
            buttons: {
                "Confirm": function() {
                    $( this ).dialog( "close" );
                }
            }
        });
        $("#datepicker").datepicker({
            altFormat: "D",
            altField: "#dayOfDate",
            dateFormat: "yy/mm/dd"
        });
        
     
       
	});
        
        myfunc=function(elem){
                var date=$(elem).val();
                if(date!=""){
                    var dataString = 'day='+ $('#dayOfDate').val();
                    //alert(1);
                    $.ajax
                    ({
                        type: "POST",
                        url: "getTimeSlotsOfDay",
                        data: dataString,
                        cache: false,
                        success: function(html)
                        {
                            $("#timeslot").html(html);
                        } 
                    });
                    $("#submit-book-app").button("enable");
                }
        };

</script>

<body>
	<div class="screen">
        <div class="back-button-div"><a href="appointments"><button id="back-button">Back</button></a></div>
        <div class="logout-button-div"><a href="logout" id="logout-button" >Logout</a></button></div>
		<div class="title">
        	Book by Time Slot
        </div>
       
        <div class="screen-div" style="max-height: 700px">
            <div class="screen-div-title">Select a time-slot for appointment in <i><% out.print(department); %></i> Dept.</div>
            <form action="bookByTimeSlot2" method="post" class="ui-form" style="margin-top: 30px; text-align: center;">
                <input type="hidden" id="dayOfDate" name="dayOfDate">
                <input type="hidden" name="dep_name" value="<% out.print(department); %>"/>
                <span class="step-title">Step 1. </span> Select a date :<input type="text" id="datepicker" name="date" onchange="myfunc(this)"/></br><br/>
                <span class="step-title">Step 2. </span> Select a timeslot :<select id="timeslot" name="timeslot"><option>select a date</option></select></br><br/>
                <span class="step-title">Step 3. </span> <input type="submit" value="Proceed" id="submit-book-app" disabled/>
            </form>
        </div>				
	</div>
    <div class="confirm" title="Please Confirm">
        <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you sure to book this appointment?
    </div>
</body>
</html>
