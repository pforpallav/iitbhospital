<%@page import="java.sql.ResultSet"%>
<html>
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
	});
        chooseDoctor = function (doc_id)
        {
            
            $('#doc_id').val(doc_id);
            $("#chooseForm").submit();
            
        };

</script>

<body>
    <% 
                    
                    ResultSet doctors=(ResultSet)request.getAttribute("doctors");
                    String department=(String)request.getAttribute("department");
                    String time_slot_id=(String)request.getAttribute("time_slot_id");
                    String date=(String)request.getAttribute("date");
                %>
    <form action="bookAppointment" method="post" id="back_form">
        <input type="hidden" name="option" value="by-timeslot">
        <input type="hidden" name="dept" value='<% out.print(department); %>'>
    </form>
	<div class="screen">
        <div class="back-button-div"><button id="back-button" onclick="$('#back_form').submit();">Back</button></div>
        <div class="logout-button-div"><a href="logout" id="logout-button" >Logout</a></div>
		<div class="title">
        	Book by Time Slot 
        </div>
        
                <form id="book_appointment" action="addAppointment" method="post">
        <input type='hidden' name="date" value='<% out.print(date);%>'/>
        <input type='hidden' name="time_slot" value='<% out.print(time_slot_id);%>'/>
        <input type='hidden' id="doc_id" name="doc_id" value=""/>
    </form>
        <div class="screen-div" style="max-height: 700px">
            <div class="screen-div-title">Available doctors in <%out.print(department); %> Dept.</div>
            <table class="table" summary="Current Appointments">
                <thead>
                    <tr>
                        <th scope="col">Doctor's Name</th>
                        <th scope="col">Quali.</th>
                        <th scope="col">Designation</th>
                        <th scope="col">Age</th>
                        <th scope="col"> </th>
                    </tr>
                </thead>
                
                <tbody>
                    <% while(doctors.next()){%>
                    <tr>
                        <td><% out.print(doctors.getString("name")); %></td>
                        <td><% out.print(doctors.getString("qualifications")); %></td>
                        <td><% out.print(doctors.getString("designation")); %></td>
                        <td><% out.print(doctors.getString("age")); %></td>
                        <td class="cancel-app" onclick="$('#doc_id').val('<% out.print(doctors.getString("doc_id")); %>');$('#book_appointment').submit();">Choose</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>				
	</div>
</body>
</html>
