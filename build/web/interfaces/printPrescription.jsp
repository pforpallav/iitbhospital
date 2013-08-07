<%-- 
    Document   : print_pinkslip
    Created on : Nov 6, 2012, 1:45:17 AM
    Author     : Pulkit
--%>


<%@page import="java.util.StringTokenizer"%>
<%@page import="com.itextpdf.text.pdf.PdfPCell"%>
<%@page import="com.itextpdf.text.pdf.PdfPTable"%>
<%@page import="com.itextpdf.text.pdf.PdfWriter"%>
<%@page import="com.itextpdf.text.FontFactory"%>
<%@page import="com.itextpdf.text.pdf.BaseFont"%>
<%@page import="com.itextpdf.text.Font"%>
<%@page import="com.itextpdf.text.Rectangle"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.itextpdf.text.Document"%>
<%@page import="com.itextpdf.text.Paragraph"%>
<%@page import="com.itextpdf.text.*"%>
<%
    HttpSession session1=request.getSession();
    //String name=(String)session1.getAttribute("username");
    String prescription_id=(String)session1.getAttribute("prescription_id");
     String patient_id=(String)session1.getAttribute("patient_id");
     String patient_name=(String)session1.getAttribute("patient_name");
      String doc_name=(String)session1.getAttribute("doc_name");
      String refer_to=(String)session1.getAttribute("refer_to");
      String druglist=(String)session1.getAttribute("druglist");
      String refer_from=(String)session1.getAttribute("refer_from");
      String extra_notes=(String)session1.getAttribute("extra_notes");
    //String pinkslip_id=(String)pinkslip_info.getString("pinkslip_id");
      StringTokenizer st=new StringTokenizer(druglist,",");
   response.setContentType("application/pdf");
   Document document = new Document();
   PdfWriter.getInstance(document, response.getOutputStream());
   document.setPageSize(new Rectangle(600, 500));
   document.setMargins(40, 40, 40, 40);
   document.addTitle("Prescription");
   document.open();
   Font f = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
   Paragraph p = new Paragraph("Prescription", f);
   p.setAlignment(1);
   document.add(p);
   PdfPTable t = new PdfPTable(4);
   BaseColor b = new BaseColor(220,220,220);
   
   t.setWidthPercentage(80);
   t.setWidths(new int[]{1,1,1,1});
   t.setSpacingBefore(30);
   
   PdfPCell c = new PdfPCell(new Phrase("Prespcription ID :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase(prescription_id));
   t.addCell(c);
   c = new PdfPCell(new Phrase("Patient ID :"));
   c.setBackgroundColor(b);
   t.addCell(c);   
   c = new PdfPCell(new Phrase(patient_id));
   t.addCell(c);
   
   c = new PdfPCell(new Phrase("Patient's Name :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase(patient_name));
   c.setColspan(3);
   t.addCell(c);
   
   c = new PdfPCell(new Phrase("Doctor's Name :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase("Dr. "+doc_name));
   c.setColspan(3);
   t.addCell(c);
   
   c = new PdfPCell(new Phrase("Refer From :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase("Dr. "+refer_from));
   t.addCell(c);
   c = new PdfPCell(new Phrase("Refer To :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase("Dr. "+refer_to));
   t.addCell(c);
   
   c = new PdfPCell(new Phrase(" "));
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   c.setBorderWidthRight(0);
   t.addCell(c);
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   c.setBorderWidthRight(0);
   c.setBorderWidthLeft(0);
   t.addCell(c);
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   c.setBorderWidthRight(0);
   c.setBorderWidthLeft(0);
   t.addCell(c);
   c = new PdfPCell(new Phrase(" "));
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   c.setBorderWidthLeft(0);
   t.addCell(c);
   
   c = new PdfPCell(new Phrase(" "));
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   c.setBorderWidthTop(0);
   t.addCell(c);
   c = new PdfPCell(new Phrase("Drug Name"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase("Quantity"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase(" "));
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   c.setBorderWidthTop(0);
   t.addCell(c);
   
   while(st.hasMoreTokens())
   {
                c = new PdfPCell(new Phrase(" "));
                c.setBackgroundColor(b);
                c.setBorderWidthBottom(0);
                c.setBorderWidthTop(0);
                t.addCell(c);
                c = new PdfPCell(new Phrase(st.nextToken()));
                t.addCell(c);
                c = new PdfPCell(new Phrase(st.nextToken()));
                t.addCell(c);
                c = new PdfPCell(new Phrase(" "));
                c.setBackgroundColor(b);
                c.setBorderWidthBottom(0);
                c.setBorderWidthTop(0);
                t.addCell(c);
   }
   
   c = new PdfPCell(new Phrase(" "));
   c.setBackgroundColor(b);
   c.setBorderWidthTop(0);
   c.setBorderWidthRight(0);
   t.addCell(c);
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   c.setBorderWidthRight(0);
   c.setBorderWidthLeft(0);
   t.addCell(c);
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   c.setBorderWidthRight(0);
   c.setBorderWidthLeft(0);
   t.addCell(c);
   c = new PdfPCell(new Phrase(" "));
   c.setBackgroundColor(b);
   c.setBorderWidthTop(0);
   c.setBorderWidthLeft(0);
   t.addCell(c);
   
   c = new PdfPCell(new Phrase("Note :"));
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   t.addCell(c);
   c = new PdfPCell(new Phrase(extra_notes));
   c.setColspan(3);
   c.setRowspan(2);
   t.addCell(c);
   c = new PdfPCell(new Phrase(" "));
   c.setBorderWidthTop(0);
   c.setBackgroundColor(b);
   t.addCell(c);
   
   
   document.add(t);
   document.close();
%>

