<tbody>
                    <% for(int i=0;i<length;i++){%>
                    <tr>
                        <td><% out.print(appointments[0]); %></td>
                        <td><% out.print(appointments[1]); %></td>
                        <td><% out.print(appointments[2]); %></td>
                        <td><% out.println(appointments[3]); %> - <% out.println(appointments[4]); %></td>
                        <td class="cancel-app" onclick="$('.confirm-cancel').dialog('open')">Cancel</td>
                    </tr>
                    <% }%>
                    <tfoot>
                        <tr>
                            <td colspan="5"><em><a href="#">Other appointments >></a></em></td>
                        </tr>
                    </tfoot>
                </tbody>