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
@WebServlet(name = "doctor_medical_leave", urlPatterns = {"/doctor_medical_leave"})
public class doctorMedicalLeavedumb extends HttpServlet {

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
    private static ConfigFetcher fetcher = new ConfigFetcher();
    private static final String DBNAME = fetcher.fetchDBNAME();
    private static final String DB_USERNAME = fetcher.fetchDBUSER();
    private static final String DB_PASSWORD = fetcher.fetchDBPASS();
    private static final String DBSERVER = fetcher.fetchDBSERVER();
    private static final String INSERT_LEAVE_QUERY="INSERT INTO pinkslip (patient_id, doc_id, start_date, end_date,note) VALUES (?,?,?,?,?);";
     private static final String DOC_NAME_QUERY = "Select name from doctor where doc_id=?";
     private static final String PINKSLIP_ID_QUERY = "Select pinkslip_id from pinkslip where patient_id=? and doc_id=? and start_date=? and end_date=?";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                HttpSession session = request.getSession();
                String username=(String)session.getAttribute("username");
                String password=(String)session.getAttribute("password");
                doctorCheckLoginObjdumb clo = new doctorCheckLoginObjdumb();
                System.out.println("doc_id"+username);
                System.out.println("password"+password);
                int isLoggedIn=clo.isLoggedIn(username, password);
                if(isLoggedIn==0)
                {
                    response.sendRedirect("doctor/index.html");
                }
                else
                {
                    //processRequest(request, response);
                
         Connection con=null;
        try {
            //processRequest(request, response);
           
            String patient_id = request.getParameter("patient_id").toString();
            String date_from = request.getParameter("date_from").toString();
            String date_to = request.getParameter("date_to").toString();
            String notes = request.getParameter("notes").toString();
            //int app = Integer.parseInt(app_id);
            //System.out.println("app id is"+app_id);
            con = connect();
                PreparedStatement prepStmt = con.prepareStatement(INSERT_LEAVE_QUERY);
                prepStmt.setString(1, patient_id);
                prepStmt.setString(2, username);
                prepStmt.setString(3, date_from);
                prepStmt.setString(4, date_to);
                prepStmt.setString(5, notes);
                int rs = prepStmt.executeUpdate();
                PreparedStatement prepStmt2=con.prepareStatement(DOC_NAME_QUERY);
                prepStmt2.setString(1, username);
                ResultSet rs2 = prepStmt2.executeQuery();
                String doc_name="";
                if(rs2.next())
                {
                    doc_name=rs2.getString("name");
                }
                PreparedStatement prepStmt3=con.prepareStatement(PINKSLIP_ID_QUERY);
                prepStmt3.setString(1, patient_id);
                prepStmt3.setString(2, username);
                prepStmt3.setString(3, date_from);
                prepStmt3.setString(4, date_to);
                ResultSet rs3 = prepStmt3.executeQuery();
                String pinkslip_id="";
                if(rs3.next())
                {
                    pinkslip_id=rs3.getString("pinkslip_id");
                }
                session.setAttribute("patient_id",patient_id);
                session.setAttribute("doc_id",username);
                session.setAttribute("name",doc_name);
                session.setAttribute("start_date",date_from);
                session.setAttribute("end_date",date_to);
                session.setAttribute("note",notes);
                session.setAttribute("pinkslip_id",pinkslip_id);
                response.sendRedirect("interafces/print_pinkslip.jsp");
        } catch (Exception ex) {
            Logger.getLogger(cancelAppointment.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(cancelAppointment.class.getName()).log(Level.SEVERE, null, ex);
                    }
        }
        
        
        
    }}
    
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

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
