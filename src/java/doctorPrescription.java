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
import java.text.ParseException;
import java.util.StringTokenizer;
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
@WebServlet(name = "doctorPrescription", urlPatterns = {"/doctorPrescription"})
public class doctorPrescription extends HttpServlet {

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
    private static final String INSERT_PRESCRIPTION_QUERY="INSERT INTO prescription (doc_id, patient_id, date, refer_to, refer_from, extra_notice) VALUES (?, ?, NOW(), ?, ?, ?);";
     private static final String INSERT_DRUGS_QUERY = "INSERT INTO prescription_has_drugs (prescription_id, drug_id, quantity) VALUES (?, ?, ?);";
     private static final String LAST_INSERTION_QUERY="SELECT DISTINCT LAST_INSERT_ID() as prescription_id FROM PRESCRIPTION;";
    private static final String DOC_NAME_QUERY="SELECT name from doctor where doc_id=?;";
    private static final String PATIENT_NAME_QUERY="(SELECT name from student where roll_no=?) union (SELECT name from staff where staff_id=?);";
    private static final String DRUG_NAME_QUERY="SELECT name from drugs where drug_id=?;"; 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Connection con=null;
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
                else{
        
        //st.nextToken();
        
            /* TODO output your page here. You may use following sample code. */
            String druglist=request.getParameter("drugs-list").toString();
            String patient_id=request.getParameter("patient_id").toString();
            String refer_to=request.getParameter("refer_to").toString();
            String refer_from=request.getParameter("refer_from").toString();
            String extra_notes=request.getParameter("extra_notes").toString();
            StringTokenizer st=new StringTokenizer(druglist,",");
            String drug_id,quantity;
            //    out.println(druglist);
             con = connect();
             System.out.println(username);
             System.out.println(patient_id);
             System.out.println(refer_to);
             System.out.println(refer_from);
             System.out.println(extra_notes);
            PreparedStatement prepStmt = con.prepareStatement(INSERT_PRESCRIPTION_QUERY);
            prepStmt.setString(1, username);
            prepStmt.setString(2, patient_id);
            prepStmt.setString(3, refer_to);
            prepStmt.setString(4, refer_from);
            prepStmt.setString(5, extra_notes);
                int rs = prepStmt.executeUpdate();
                PreparedStatement prepStmt2 = con.prepareStatement(LAST_INSERTION_QUERY);
                ResultSet rs2=prepStmt2.executeQuery();
                rs2.next();
                String prescription_id=rs2.getString("prescription_id");
                //out.println("prescreption_id= "+rs2.getString("prescription_id"));
                PreparedStatement prepStmt3 = con.prepareStatement(INSERT_DRUGS_QUERY);
                PreparedStatement prepStmt7 = con.prepareStatement(DRUG_NAME_QUERY);
            String my_druglist="";
                while(st.hasMoreTokens())
            {
                prepStmt3.setString(1,prescription_id);
                String my_drug_id=st.nextToken();
                String my_drug_quantity=st.nextToken();
                prepStmt3.setString(2,my_drug_id);
                prepStmt3.setString(3,my_drug_quantity);
                int rs3 = prepStmt3.executeUpdate();
                prepStmt7.setString(1,my_drug_id);
                ResultSet rs7=prepStmt7.executeQuery();
                rs7.next();
                my_druglist=my_druglist + "," +rs7.getString("name") +"," +my_drug_quantity;
            }
            PreparedStatement prepStmt4 = con.prepareStatement(PATIENT_NAME_QUERY);
            PreparedStatement prepStmt5 = con.prepareStatement(DOC_NAME_QUERY);
            prepStmt4.setString(1,patient_id);
            prepStmt4.setString(2,patient_id);
            prepStmt5.setString(1,username);
            ResultSet rs4=prepStmt4.executeQuery();
            rs4.next();
            ResultSet rs5=prepStmt5.executeQuery();
            rs5.next();
            session.setAttribute("patient_id", patient_id);
            session.setAttribute("prescription_id", prescription_id);
            session.setAttribute("druglist", my_druglist);
            session.setAttribute("extra_notes", extra_notes);
            session.setAttribute("patient_name", rs4.getString("name"));
            session.setAttribute("doc_name", rs5.getString("name"));
            prepStmt5.setString(1,refer_to);
            rs5=prepStmt5.executeQuery();
            rs5.next();
            session.setAttribute("refer_to", rs5.getString("name"));
            prepStmt5.setString(1,refer_from);
            rs5=prepStmt5.executeQuery();
            
            rs5.next();
            session.setAttribute("refer_from", rs5.getString("name"));
            
            response.sendRedirect("interfaces/printPrescription.jsp");
         }
    }
    Connection connect() throws Exception
    {
        Connection con=null;
        try
        {
            String url = "jdbc:mysql://10.3.113.29:3306/"+DBNAME+"?user="+DB_USERNAME+"&password="+DB_PASSWORD;
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
        } catch (Exception ex) {
            Logger.getLogger(doctorPrescription.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (Exception ex) {
            Logger.getLogger(doctorPrescription.class.getName()).log(Level.SEVERE, null, ex);
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
