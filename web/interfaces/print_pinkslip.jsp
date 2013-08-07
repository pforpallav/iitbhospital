<%-- 
    Document   : print_pinkslip
    Created on : Nov 6, 2012, 1:45:17 AM
    Author     : Pulkit
--%>


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
    String pinkslip_id=(String)session1.getAttribute("pinkslip_id");
     String patient_id=(String)session1.getAttribute("patient_id");
     String patient_name=(String)session1.getAttribute("patient_name");
      String doc_name=(String)session1.getAttribute("doc_name");
      String start_date=(String)session1.getAttribute("start_date");
      String end_date=(String)session1.getAttribute("end_date");
      String note=(String)session1.getAttribute("note");
    //String pinkslip_id=(String)pinkslip_info.getString("pinkslip_id");
   response.setContentType("application/pdf");
   Document document = new Document();
   PdfWriter.getInstance(document, response.getOutputStream());
   document.setPageSize(new Rectangle(600, 300));
   document.setMargins(40, 40, 40, 40);
   document.addTitle("Pinkslip/Medical Leave");
   document.open();
   Font f = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
   Paragraph p = new Paragraph("Pinkslip/Medical Leave", f);
   p.setAlignment(1);
   document.add(p);
   PdfPTable t = new PdfPTable(4);
   BaseColor b = new BaseColor(220,220,220);
   
   t.setWidthPercentage(80);
   t.setWidths(new int[]{1,1,1,1});
   t.setSpacingBefore(30);
   
   PdfPCell c = new PdfPCell(new Phrase("PinkSlip ID :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase(pinkslip_id));
   t.addCell(c);
   c = new PdfPCell(new Phrase("Patient ID :"));
   c.setBackgroundColor(b);
   t.addCell(c);   
   c = new PdfPCell(new Phrase(patient_id));
   t.addCell(c);
   
   c = new PdfPCell(new Phrase("Patient Name :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase(patient_name));
   c.setColspan(3);
   t.addCell(c);
   
   c = new PdfPCell(new Phrase("Start Date :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase(start_date));
   t.addCell(c);
   c = new PdfPCell(new Phrase("End Date :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase(end_date));
   t.addCell(c);
   
   c = new PdfPCell(new Phrase("Note :"));
   c.setBackgroundColor(b);
   c.setBorderWidthBottom(0);
   t.addCell(c);
   c = new PdfPCell(new Phrase(note));
   c.setColspan(3);
   c.setRowspan(2);
   t.addCell(c);
   c = new PdfPCell(new Phrase(" "));
   c.setBorderWidthTop(0);
   c.setBackgroundColor(b);
   t.addCell(c);
   
   c = new PdfPCell(new Phrase("Issued by :"));
   c.setBackgroundColor(b);
   t.addCell(c);
   c = new PdfPCell(new Phrase("Dr. " + doc_name));
   c.setColspan(3);
   t.addCell(c);
   
   document.add(t);
   document.close();
%>
