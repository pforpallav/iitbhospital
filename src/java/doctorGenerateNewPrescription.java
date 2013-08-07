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
@WebServlet(name = "doctorGenerateNewPrescription", urlPatterns = {"/doctorGenerateNewPrescription"})
public class doctorGenerateNewPrescription extends HttpServlet {

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
    private static final String DRUGS_QUERY="SELECT drug_id,name from drugs";
     private static final String DOCTORS_QUERY = "Select doc_id,name from doctor ";
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, Exception {
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
                     con = connect();
                    PreparedStatement prepStmt = con.prepareStatement(DRUGS_QUERY);
                    ResultSet rs = prepStmt.executeQuery();
                    PreparedStatement prepStmt2 = con.prepareStatement(DOCTORS_QUERY);
                    ResultSet rs2 = prepStmt2.executeQuery();
                    request.setAttribute("drugs",rs);
                    request.setAttribute("doctors",rs2);
                    request.getRequestDispatcher("doctor/new_prescription.jsp").forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(doctorGenerateNewPrescription.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(doctorGenerateNewPrescription.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(doctorGenerateNewPrescription.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(doctorGenerateNewPrescription.class.getName()).log(Level.SEVERE, null, ex);
        }
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
