import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class create_pdf extends HttpServlet {


protected void processRequest(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
response.setContentType("text/html;charset=UTF-8");
PrintWriter out = response.getWriter();
try {
OutputStream file = new FileOutputStream(new File("C://text.pdf"));

Document document = new Document();
PdfWriter.getInstance(document, file);
document.open();
String name=request.getParameter("username");
String age=request.getParameter("age");
String lname=request.getParameter("userlname");
document.add(new Paragraph("i am sujesh "));
document.add(new Paragraph(new Date().toString()));
document.add(new Paragraph(name));
document.add(new Paragraph(" "));
document.add(new Paragraph(lname));
document.add(new Paragraph(" "));
document.add(new Paragraph(age));

document.close();
file.close();
out.println("<table>");
out.println("<tr>");
out.println("<th><h1>" + name + "</h1></th>");
out.println("</tr>");
out.println("<th><h1>" + age + "</h1></th>");
out.println("<tr>");
out.println("<th><h1>" + lname + "</h1></th>");
out.println("</tr>");
out.println("<div align='center'><h1> succefully created pdf</h1></div>");


} catch (Exception e) {
} finally {
out.close();
}
} 


@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
    System.out.println("pdf");
processRequest(request, response);
} 


@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
processRequest(request, response);
}


@Override
public String getServletInfo() {
return "Short description";
}
}