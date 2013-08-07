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
@WebServlet(name = "bookByDoctor2", urlPatterns = {"/bookByDoctor2"})
public class bookByDoctor2 extends HttpServlet {

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
    private static final String DOCTOR_QUERY="Select doctor.name as doc_name,doc_id,department.name as dep_name   from doctor,department where doc_id=? and doctor.department_id=department.department_id";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        
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
                    try {
                        processRequest(request, response);
                    } catch (Exception ex) {
                        Logger.getLogger(bookByDoctor2.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
                
        Connection con=null;
        try {
            
            String doc_id = request.getParameter("doc_id").toString();
            
                /* TODO output your page here. You may use following sample code. */
                
                //out.println("<h1>Doc id is " + doc_id + "</h1>");
                con = connect();
                PreparedStatement prepStmt = con.prepareStatement(DOCTOR_QUERY);
                prepStmt.setString(1, doc_id);
                ResultSet rs = prepStmt.executeQuery();
                request.setAttribute("doctor",rs);
                request.getRequestDispatcher("interfaces/book_by_doctor2.jsp").forward(request, response);
                //response.sendRedirect("interfaces/menu.html");
        } catch (Exception ex) {
            Logger.getLogger(bookByDoctor2.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(bookByDoctor2.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
