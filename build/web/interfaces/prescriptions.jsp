<%@page import="java.sql.ResultSet"%>
<html>
<title>IITB Hospital - Prescriptions</title>
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
            altFormat: "DD",
            altField: "#dayOfDate",
            dateFormat: "dd/mm/yy"
        });
        $("#datepicker").change(function(){
                var id=$(this).val();
                var dataString = 'date='+ id +'day=+';

                $.ajax
                ({
                    type: "POST",
                    url: "ajax_city.php",
                    data: dataString,
                    cache: false,
                    success: function(html)
                    {
                        $(".city").html(html);
                    } 
                });
        });
	});

</script>

<body>
    <% ResultSet prescriptions=(ResultSet)request.getAttribute("prescriptions"); %>
    <form action="showPrescription" id="show_prescription" name="showPrescription" method="post">
        <input type="hidden" value="" id="prescription_id" name="prescription_id"/>
    </form>
	<div class="screen">
            <div class="back-button-div"><a href="interfaces/menu.jsp"><button id="back-button">Back</button></a></div>
            <div class="logout-button-div"><a href="logout"><button id="logout-button">Logout</button></a></div>
		<div class="title">
        	Prescriptions
        </div>
        <div class="screen-div" style="max-height: 700px">
            <div class="screen-div-title">Prescriptions</div>
            <div class="table-div" style="max-height: 300px">
            <table class="table" summary="My Prescriptions">
                <thead>
                    <tr>
                        <th scope="col">Prescription ID</th>
                        <th scope="col">Date</th>
                        <th scope="col">Doctor</th>
                        <th scope="col">Department</th>
                        <th scope="col">Fee</th>
                    </tr>
                </thead>
                <tbody>
                    <%while(prescriptions.next()){%>
                    <tr onclick="$('#prescription_id').val('<%out.print(prescriptions.getString("prescription_id"));%>');$('#show_prescription').submit();" style="cursor:pointer">
                        <td><% out.print(prescriptions.getString("prescription_id")); %></td>
                        <td><% out.print(prescriptions.getString("date")); %></td>
                        <td><% out.print(prescriptions.getString("doc_name")); %></td>
                        <td><% out.print(prescriptions.getString("dep_name")); %></td>
                        <td><% out.print(prescriptions.getString("fee")); %></td>
                    </tr>
                    <% } %>
                    <tfoot>
                        <tr>
                            <td colspan="5"><em>Click to view/print details</a></td>
                        </tr>
                    </tfoot>
                </tbody>
            </table>
            </div>
        </div>				
	</div>
    <div class="confirm" title="Please Confirm">
        <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you sure to book this appointment?
    </div>
</body>
</html>
