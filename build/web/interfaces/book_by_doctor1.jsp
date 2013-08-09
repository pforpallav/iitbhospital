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
    <form id="chooseForm" action="bookByDoctor2" method="post">
        <input type='hidden' id="doc_id" value="0" name="doc_id"/>
    </form>
	<div class="screen">
        <div class="back-button-div"><a href="appointments"><button id="back-button">Back</button></a></div>
        <div class="logout-button-div"><a href="logout" id="logout-button" >Logout</a></div>
		<div class="title">
        	Book by Doctor
        </div>
        <% 
                    
                    ResultSet doctors=(ResultSet)request.getAttribute("doctors");
                    String department=(String)request.getAttribute("department");
                            
                %>
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
                        <td class="cancel-app" onclick="chooseDoctor(<%out.print(doctors.getString("doc_id")); %>)">Choose</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>				
	</div>
</body>
</html>
