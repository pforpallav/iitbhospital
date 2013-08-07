<%@page import="java.sql.ResultSet"%>
<html>
     <% 
                    
                    ResultSet doctor=(ResultSet)request.getAttribute("doctor");
                    doctor.next();
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
                        var dataString = 'date='+ date +'&day='+ $('#dayOfDate').val()+"&doc_id=<% out.print(doctor.getString("doc_id")); %>";
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
    <form action="bookAppointment" method="post" id="back_form">
        <input type="hidden" name="option" value="by-doctor">
        <input type="hidden" name="dept" value='<% out.print(doctor.getString("dep_name")); %>'>
    </form>
	<div class="screen">
        <div class="back-button-div"><button id="back-button" onclick="$('#back_form').submit();">Back</button></div>
        <div class="logout-button-div"><a href="logout" id="logout-button" >Logout</a></button></div>
		<div class="title">
        	Book by Doctor
        </div>
       
        <div class="screen-div" style="max-height: 700px">
            <div class="screen-div-title">Select a time-slot for appointment with</br>Dr. <i><% out.print(doctor.getString("doc_name")); %></i> in <i><% out.print(doctor.getString("dep_name")); %></i> Dept.</div>
            <form action="addAppointment" method="post" class="ui-form" style="margin-top: 30px; text-align: center;">
                <input type="hidden" id="dayOfDate" name="dayOfDate">
                <input type="hidden" name="doc_id" value="<% out.print(doctor.getString("doc_id")); %>"/>
                <span class="step-title">Step 1. </span> Select a date :<input type="text" id="datepicker" name="date" onchange="myfunc(this)"/></br><br/>
                <span class="step-title">Step 2. </span> Select a timeslot :<select id="timeslot" name="time_slot"><option>select a date</option></select></br><br/>
                <span class="step-title">Step 3. </span> <input type="submit" value="Confirm" id="submit-book-app" disabled/>
            </form>
        </div>				
	</div>
    <div class="confirm" title="Please Confirm">
        <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you sure to book this appointment?
    </div>
</body>
</html>
