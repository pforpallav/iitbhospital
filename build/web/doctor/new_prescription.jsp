<%@page import="java.sql.ResultSet"%>
<html>
    <% 
                    
                    ResultSet doctors=(ResultSet)request.getAttribute("doctors");
                    //doctors.
                    ResultSet doctors2=doctors;
                    ResultSet drugs=(ResultSet)request.getAttribute("drugs");%>
<title>IITB Hospital - New Prescription</title>
<link rel="stylesheet" type="text/css" href="doctor/Aristo.css">
<link rel="stylesheet" type="text/css" href="doctor/style.css">
<!--link href='http://fonts.googleapis.com/css?family=Ubuntu:400,700' rel='stylesheet' type='text/css'-->

<script type="text/javascript" src="doctor/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="doctor/js/jquery-ui.min.js"></script>
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

    (function( $ ) {
        $.widget( "ui.combobox", {
          _create: function() {
            var self = this;
            var select = this.element.hide(),
              selected = "",
              value = "";
            var input = $( "<input />" )
              .insertAfter(select)
              .val( value )
              .autocomplete({
                delay: 0,
                minLength: 0,
                source: function(request, response) {
                  var matcher = new RegExp( $.ui.autocomplete.escapeRegex(request.term), "i" );
                  response( select.children("option" ).map(function() {
                    var text = $( this ).text();
                    if ( this.value && ( !request.term || matcher.test(text) ) )
                      return {
                        label: text.replace(
                          new RegExp(
                            "(?![^&;]+;)(?!<[^<>]*)(" +
                            $.ui.autocomplete.escapeRegex(request.term) +
                            ")(?![^<>]*>)(?![^&;]+;)", "gi"),
                          "<strong>$1</strong>"),
                        value: text,
                        option: this
                      };
                  }) );
                },
                select: function( event, ui ) {
                  ui.item.option.selected = true;
                  self._trigger( "selected", event, {
                    item: ui.item.option
                  });
                },
                change: function(event, ui) {
                  if ( !ui.item ) {
                    var matcher = new RegExp( "^" + $.ui.autocomplete.escapeRegex( $(this).val() ) + "$", "i" ),
                    valid = false;
                    select.children( "option" ).each(function() {
                      if ( this.value.match( matcher ) ) {
                        this.selected = valid = true;
                        return false;
                      }
                    });
                    if ( !valid ) {
                      // remove invalid value, as it didn't match anything
                      $( this ).val( "" );
                      select.val( "" );
                      return false;
                    }
                  }
                }
              })
              .addClass("ui-widget ui-widget-content ui-corner-left");
           
            input.data( "autocomplete" )._renderItem = function( ul, item ) {
              return $( "<li></li>" )
                .data( "item.autocomplete", item )
                .append( "<a>" + item.label + "</a>" )
                .appendTo( ul );
            };
           
            
          }
        });
    })( jQuery );

    var drugList = [{label: "Becosules", value: 1}, {label: "Nimosulide", value: 1}, {label: "Koflex", value: 1}, {label: "Sumo", value: 1}];
    
				
	$(function(){
        
        
		$("#login-button").button();
        $("#my_submit").button();
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
        $("#refer_to").combobox();
        $("#refer_from").combobox();
        $("#new-drug-name").combobox();
	});

    removeRow = function(elem){
        $(elem).parent().remove();
    };

    addRow = function(){
        if($('tfoot input:first').val()!="" && $('#new-quantity').val()!=""){
            $('#drug-table>tbody').append('<tr><input type="hidden" value="'+$('#new-drug-name').val()+'"><td>'+$('tfoot input:first').val()+'</td><td>'+$('#new-quantity').val()+'</td><td style="cursor: pointer" onclick="removeRow(this)">Remove</td></tr>');
            $('#new-drug-name').val('');
            $('tfoot input:first').val('');
            $('#new-quantity').val('');
        }
    };

    onConfirm = function(){
        
        $("#drug-table tbody tr").each(function(){
            $("#drugs-list").val($("#drugs-list").val()+","+$(this).children("input").val()+","+$(this).children("td:nth-child(3)").text());
        });
        $('#newPrep').submit();
    };
    validatePatientId=function()
    {
        var dataString = "patient_id="+ $('#patient_id').val();
        var result;
        $.ajax
                ({
                    async:false,
                    type: "GET",
                    url: "doctorGetPatientName",
                    data: dataString,
                    cache: false,
                    success: function(html)
                    {
                        
                        result=html;
                    } 
                });
                
         if(result == "")
             {
                 $('#patient_id').val("");
                 $('#my_submit').button("disable");
                 $('#welcome-text').text("New Prescription");
             }
         else
             {
                 $('#welcome-text').text("Prescription For "+result);
                 $('#my_submit').button("enable");
             }
             
    }
    

</script>

<body>
	<div class="screen">
            <div class="back-button-div"><a id="back-button" href="doctor/menu.jsp">Back</a></div>
            <div class="logout-button-div"><a href="doctorLogout" id="logout-button">Logout</a></div>
		<div class="title">
        	Write a Prescription
        </div>
        <div class="screen-div" style="max-height: 700px">
            <div id="welcome-text" class="screen-div-title">New Prescription</div>
            <form action="doctorPrescription" method="post" name="newPrep" id="newPrep" class="ui-form" style="text-align: center;">

            
            <table id="drug-table" class="table" summary="Current Appointments" style="width: auto;">
                <thead>
                    <tr>
                        <th scope="col">Drug Name</th>
                        <th scope="col">Quantity</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    
                    <tfoot>
                        
                        <tr>
                            <td>
                                <select name="new-drug-name" id="new-drug-name" value="">
                                    <% while(drugs.next()){%>
                                    <option value="<% out.print(drugs.getString("drug_id")); %>"><% out.print(drugs.getString("name")); %></option>
                                    <% } %>
                                </select>
                            </td>
                            <td><input type="text" id="new-quantity"/></td>
                            <td style="cursor: pointer" onclick="addRow()"><em>Add</em></td>
                        </tr>
                        
                    </tfoot>
                </tbody>
            </table>

            <input type="hidden" value="" id="drugs-list" name="drugs-list">

            <div class="label-set">
                <label for="patient_id">Patient ID: </label>
                <input type="text" name="patient_id" id="patient_id" onchange="validatePatientId()"></br>
            </div>

            <div class="label-set">
                <label for="patient_id">Refer To: </label>
                <select name="refer_to" id="refer_to" value="">
                    <option value="0"></option>
                    <% while(doctors.next()){%>
                                    <option value="<% out.print(doctors.getString("doc_id")); %>"><% out.print(doctors.getString("name")); %></option>
                                    <% } doctors.beforeFirst();%>
                </select></br>
            </div>
            <div class="label-set">
                <label for="patient_id">Refer From: </label>
                <select name="refer_from" id="refer_from" value="">
                    <option value="0"></option>
                    <% while(doctors.next()){%>
                                    <option value="<% out.print(doctors.getString("doc_id")); %>"><% out.print(doctors.getString("name")); %></option>
                                    <% } %>
                </select></br>
            </div>    

            <div class="label-set" style="height: 100px">
                <label for="patient_id">Extra Notes: </label>
                <textarea name="extra_notes" id="extra_notes" style="resize:none; height:100px"></textarea> 
            </div>

                <input type="button" value="Confirm"  id="my_submit" onclick="onConfirm();" style="margin-top:20px" disabled>

            </form>
        </div>				
	</div>
    <div class="confirm" title="Please Confirm">
        <span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>Are you sure to book this appointment?
    </div>
</body>
</html>
