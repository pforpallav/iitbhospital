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
 * @author Pulkit
 */
@WebServlet(name = "appointments", urlPatterns = {"/appointments"})
public class appointments extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static ConfigFetcher fetcher = new ConfigFetcher();
    private static final String DBNAME = fetcher.fetchDBNAME();
    private static final String DB_USERNAME = fetcher.fetchDBUSER();
    private static final String DB_PASSWORD = fetcher.fetchDBPASS();
    private static final String DBSERVER = fetcher.fetchDBSERVER();
    private static final String APPOINTMENT_QUERY = "SELECT appointment_id, doctor.name as doc_name, department.name as dep_name, date, start_time, end_time FROM doctor, department, appointments, timeslot WHERE appointments.patient_id=? and appointments.date>=NOW() and appointments.doc_id=doctor.doc_id and doctor.department_id=department.department_id and appointments.time_slot_id=timeslot.time_slot_id;";
    private static final String DOCTOR_INFO_QUERY = "select doctor.name as doc_name,department.name as dep_name  from doctor,department where doc_id=? and doctor.department_id=department.department_id";
    private static final String TIMESLOT_INFO_QUERY = "select * from timeslot where timeslot_id=?";
    private static final String DEPARTMENT_INFO_QUERY = "select * from department";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println("new execution");
        Connection con=null;
        try {
            
            HttpSession session = request.getSession();
            String username=(String)session.getAttribute("username");
            String password=(String)session.getAttribute("password");
            
            
            //authenticate login 
            
            String appointments[][]=new String[3][5];
            
            con = connect();
            PreparedStatement prepStmt = con.prepareStatement(APPOINTMENT_QUERY);
            prepStmt.setString(1, username);
            ResultSet rs = prepStmt.executeQuery();
            /*int count=0;
            while(rs.next()) 
            {
                    //System.out.println("User login is valid in DB");
                    int doc_id=rs.getInt("doc_id");
                    prepStmt1.setInt(1, doc_id);
                    
                   // prepStmt.setString(2, strPassword);
                    ResultSet rs1 = prepStmt1.executeQuery();
                    appointments[count][0]=rs1.getString("doc_name");
                    appointments[count][1]=rs1.getString("dep_name");
                    appointments[count][2]=rs.getString("date");
                    int timeslot_id=rs.getInt("time_slot_id");
                    prepStmt2.setInt(1, timeslot_id);
                   // prepStmt.setString(2, strPassword);
                    ResultSet rs2 = prepStmt2.executeQuery();
                    appointments[count][3]=rs2.getString("start_time");
                    appointments[count][4]=rs.getString("end_time");
                    count++;
            }*/
            //String departments[]=new String[];
            PreparedStatement prepStmt3 = con.prepareStatement(DEPARTMENT_INFO_QUERY);
            //prepStmt.setString(1, username);
                   // prepStmt.setString(2, strPassword);
            ResultSet rs3 = prepStmt3.executeQuery();
            
            request.setAttribute("appointments",rs);
            request.setAttribute("departments",rs3);
            request.getRequestDispatcher("interfaces/appointments.jsp").forward(request, response);
        
            //response.sendRedirect("interfaces/appointments.jsp");
        } finally {            
            out.close();
            con.close();
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
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
                    try {
                        processRequest(request, response);
                    } catch (Exception ex) {
                        Logger.getLogger(appointments.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
                    try {
                        processRequest(request, response);
                    } catch (Exception ex) {
                        Logger.getLogger(appointments.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
