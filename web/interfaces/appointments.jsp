<%@page import="java.sql.ResultSet"%>
<html>
<title>IITB Hospital - Appointments</title>
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
        $(".confirm-cancel").dialog({
            autoOpen: false,
            draggable: false,
            modal: true,
            resizable: false,
            buttons: {
                "Confirm": function() {
                    $( this ).dialog( "close" );
                    $("#confirmForm").submit();
                }
            }
        });
	});
        
        confirmCancel = function (app_id)
        {
            
            $('#app_id').val(app_id);
            $(".confirm-cancel").dialog('open');
            
        };
        
        decideBookMethod = function()
        {
            //alert($('#bookapp').action);
            $("#bookapp").submit();
        };

</script>

<body>
    <form id="confirmForm" action="cancelAppointment" method="post">
        <input type='hidden' id="app_id" value="0" name="app_id"/>
    </form>
	<div class="screen">
        <div class="back-button-div"><a href="interfaces/menu.jsp"><button id="back-button">Back</button></a></div>
        <div class="logout-button-div"><a href="logout" id="logout-button" >Logout</a></div>
		<div class="title">
        	My Appointments
        </div>
        <div class="screen-div">
            <div class="screen-div-title">Current appointments</div>
            <div class="table-div">
            <table class="table" summary="Current Appointments">
                <thead>
                    <tr>
                        <th scope="col">Doctor's Name</th>
                        <th scope="col">Dept.</th>
                        <th scope="col">Date</th>
                        <th scope="col">Slot</th>
                        <th scope="col"> </th>
                    </tr>
                </thead>
                <% 
                    //String appointments[][]=(String[][])request.getAttribute("appointments");
                    //int length=appointments.length;
                    ResultSet appointments=(ResultSet)request.getAttribute("appointments");
                    ResultSet departments=(ResultSet)request.getAttribute("departments");
                            
                %>
                <tbody>
                    <% while(appointments.next()){%>
                    <tr>
                        <td><% out.print(appointments.getString("doc_name")); %></td>
                        <td><% out.print(appointments.getString("dep_name")); %></td>
                        <td><% out.print(appointments.getString("date")); %></td>
                        <td><% out.print(appointments.getString("start_time")); %> - <% out.print(appointments.getString("end_time")); %></td>
                        <td class="cancel-app" onclick="confirmCancel(<%out.print(appointments.getString("appointment_id")); System.out.println("appointment is ");System.out.print(appointments.getString("appointment_id")); %>)">Cancel</td>
                    </tr>
                    <% }%>
                    <tfoot>
                        <tr>
                            <td colspan="5"><em>Click on cancel to delete the respective appointment.</em></td>
                        </tr>
                    </tfoot>
                </tbody>
                
            </table>
            </div>
        </div>
        <div class="screen-div" height="200px">
            <div class="screen-div-title">Book appointment</div>
            <div class="screen-div-inside">
                <form action="bookAppointment" method="post" name="bookapp" id="bookapp" class="ui-form">
                    Book an appointment in the
                    <select name="dept">
                        <% while(departments.next()){ %>
                        <option vaule=<% out.print(departments.getString("name")); %>>
                            <% out.print(departments.getString("name")); %>
                        </option>
                        <% } %>
                    </select> dept. by
                    <select name="option" id="option">
                        <option value="by-doctor">
                            Doctor
                        </option>
                        <option value="by-timeslot">
                            Time-slot 
                        </option>
                    </select>
                    <input type="submit" value="Go" id="submit" />
                </form>
            </div>
        </div>
				
	</div>
    <div class="confirm-cancel" title="Please Confirm">
        <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you sure to cancel this appointment?
    </div>
</body>
</html>
