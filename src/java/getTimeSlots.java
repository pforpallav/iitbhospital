/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author
 * Pulkit
 */
@WebServlet(name = "getTimeSlots", urlPatterns = {"/getTimeSlots"})
public class getTimeSlots extends HttpServlet {

    /**
     * Processes
     * requests
     * for
     * both
     * HTTP
     * <code>GET</code>
     * and
     * <code>POST</code>
     * methods.
     *
     * @param
     * request
     * servlet
     * request
     * @param
     * response
     * servlet
     * response
     * @throws
     * ServletException
     * if
     * a
     * servlet-specific
     * error
     * occurs
     * @throws
     * IOException
     * if
     * an
     * I/O
     * error
     * occurs
     */
    
    private static final String DBNAME = "mydb";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "vallap";
    private static final String DBSERVER = "localhost";
    private static final String TIMESLOT_QUERY="select * from (Select start_time,end_time,timeslot.time_slot_id   from doctor_has_timeslot,timeslot where doc_id=? and doctor_has_timeslot.time_slot_id = timeslot.time_slot_id and timeslot.day=?)as t  where t.time_slot_id not in (select timeslot.time_slot_id from timeslot,(Select appointments.time_slot_id as t_id,count(*) as total from appointments where  appointments.doc_id=?   and appointments.date=?  group by appointments.time_slot_id having total=(select patient_limit from doctor_has_timeslot where doc_id=? and time_slot_id=t_id) )as full where full.t_id=timeslot.time_slot_id);";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            Connection con=null;
        try {
            System.out.println("gettimeslots called");
            String date = request.getParameter("date").toString();
            System.out.println(date);
            String day = request.getParameter("day").toString();
            day=day.toLowerCase();
            
            String doc_id = request.getParameter("doc_id").toString();
            
            System.out.println(day);
            System.out.println(doc_id);
                /* TODO output your page here. You may use following sample code. */
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(TIMESLOT_QUERY);
                prepStmt.setString(1, doc_id);
                prepStmt.setString(2, day);
                prepStmt.setString(3, doc_id);
                prepStmt.setString(4, date);
                prepStmt.setString(5, doc_id);
                ResultSet rs = prepStmt.executeQuery();
                while(rs.next())
                {
                    out.print("<option ");
                    out.print("value= "+rs.getString("time_slot_id"));
                    out.print("> ");
                    out.print(rs.getString("start_time")+" - "+rs.getString("end_time"));
                    out.print("</option>");
                    System.out.println("<option ");
                    System.out.println("value= "+rs.getString("time_slot_id"));
                    System.out.println("> ");
                    System.out.println(rs.getString("start_time")+" - "+rs.getString("end_time"));
                    System.out.println("</option>");
                }
        } catch (Exception ex) {
            Logger.getLogger(getTimeSlots.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(getTimeSlots.class.getName()).log(Level.SEVERE, null, ex);
                    }
            out.close();
        }
            
        
    }
    
    
    Connection connect() throws Exception
    {
        Connection con=null;
        try
        {
            String url = "jdbc:mysql://"+DBSERVER+"/"+DBNAME+"?user="+DB_USERNAME+"&password="+DB_PASSWORD;
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url);
        } 
        catch (SQLException sqle) 
        {
            System.out.println("SQLException: Unable to open connection to db: "+sqle.getMessage());
            throw sqle;
        }
         catch(Exception e)
        {
            System.out.println("Exception: Unable to open connection to db: "+e.getMessage());
            throw e;
        }
        
        return con;
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles
     * the
     * HTTP
     * <code>GET</code>
     * method.
     *
     * @param
     * request
     * servlet
     * request
     * @param
     * response
     * servlet
     * response
     * @throws
     * ServletException
     * if
     * a
     * servlet-specific
     * error
     * occurs
     * @throws
     * IOException
     * if
     * an
     * I/O
     * error
     * occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession();
                String username=(String)session.getAttribute("username");
                String password=(String)session.getAttribute("password");
                checkLoginObj clo = new checkLoginObj();
                int isLoggedIn=clo.isLoggedIn(username, password);
                if(isLoggedIn==0)
                {
                    response.sendRedirect("interfaces/index.html");
                }
                else
                {
                    processRequest(request, response);
                }
    }

    /**
     * Handles
     * the
     * HTTP
     * <code>POST</code>
     * method.
     *
     * @param
     * request
     * servlet
     * request
     * @param
     * response
     * servlet
     * response
     * @throws
     * ServletException
     * if
     * a
     * servlet-specific
     * error
     * occurs
     * @throws
     * IOException
     * if
     * an
     * I/O
     * error
     * occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         HttpSession session = request.getSession();
                String username=(String)session.getAttribute("username");
                String password=(String)session.getAttribute("password");
                checkLoginObj clo = new checkLoginObj();
                int isLoggedIn=clo.isLoggedIn(username, password);
                if(isLoggedIn==0)
                {
                    response.sendRedirect("interfaces/index.html");
                }
                else
                {
                    //processRequest(request, response);
                
        response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            Connection con=null;
        try {
            System.out.println("gettimeslots called");
            String date = request.getParameter("date").toString();
            String day = (request.getParameter("day").toString()).toLowerCase();
            String doc_id = request.getParameter("day").toString();
            
                /* TODO output your page here. You may use following sample code. */
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(TIMESLOT_QUERY);
                prepStmt.setString(1, doc_id);
                prepStmt.setString(2, day);
                prepStmt.setString(3, doc_id);
                prepStmt.setString(4, date);
                prepStmt.setString(5, doc_id);
                ResultSet rs = prepStmt.executeQuery();
                while(rs.next())
                {
                    out.print("<option ");
                    out.print("value= "+rs.getString("time_slot_id"));
                    out.print("> ");
                    out.print(rs.getString("start_time")+" - "+rs.getString("end_time"));
                    out.print("</option>");
                    System.out.println("<option ");
                    System.out.println("value= "+rs.getString("time_slot_id"));
                    System.out.println("> ");
                    System.out.println(rs.getString("start_time")+" - "+rs.getString("end_time"));
                    System.out.println("</option>");
                }
        } catch (Exception ex) {
            Logger.getLogger(getTimeSlots.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(getTimeSlots.class.getName()).log(Level.SEVERE, null, ex);
                    }
            out.close();
        }
    }}

    /**
     * Returns
     * a
     * short
     * description
     * of
     * the
     * servlet.
     *
     * @return
     * a
     * String
     * containing
     * servlet
     * description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
