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
@WebServlet(name = "showPinkslip", urlPatterns = {"/showPinkslip"})
public class showPinkslip extends HttpServlet {

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
    private static final String PINKSLIP_QUERY = "select * from pinkslip,doctor,patient where pinkslip_id=? and pinkslip.doc_id=doctor.doc_id and patient.patient_id=pinkslip.patient_id";
    private static final String PATIENTNAME_QUERY = "(select name from staff where staff_id=?) union (select name from student where roll_no=?)";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
            //PrintWriter out = response.getWriter();
            Connection con=null;
            String pinkslip_id = request.getParameter("pinkslip_id").toString();
            HttpSession session=request.getSession();
        try {
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(PINKSLIP_QUERY);
                prepStmt.setString(1, pinkslip_id);
                ResultSet rs = prepStmt.executeQuery();
                
                if(rs.next())
                {
                    PreparedStatement prepStmt2 = con.prepareStatement(PATIENTNAME_QUERY);
                    prepStmt2.setString(1, rs.getString("patient_id"));
                    prepStmt2.setString(2, rs.getString("patient_id"));
                    ResultSet rs2 = prepStmt2.executeQuery();
                    rs2.next();
                    session.setAttribute("pinkslip_id",rs.getString("pinkslip_id"));
                    session.setAttribute("patient_id",rs.getString("patient_id"));
                    session.setAttribute("patient_name",rs2.getString("name"));
                    session.setAttribute("doc_name",rs.getString("name"));
                    session.setAttribute("start_date",rs.getString("start_date"));
                    session.setAttribute("end_date",rs.getString("end_date"));
                    session.setAttribute("note",rs.getString("note"));
                    response.sendRedirect("interfaces/print_pinkslip.jsp");
                }else{
                    response.sendRedirect("interfaces/invalidPinkslip.html");
                }

                //request.setAttribute("pinkslip_info",rs);
                //request.getRequestDispatcher("interfaces/print_pinkslip.jsp").forward(request, response);
                //response.sendRedirect("interfaces/doc-menu.html");
        } catch (Exception ex) {
            Logger.getLogger(medical_leaves.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(medical_leaves.class.getName()).log(Level.SEVERE, null, ex);
                    }
//            out.close();
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
