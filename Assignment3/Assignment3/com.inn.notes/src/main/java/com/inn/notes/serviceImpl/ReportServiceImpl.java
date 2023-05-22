package com.inn.notes.serviceImpl;

import com.inn.notes.JWT.JwtFilter;
import com.inn.notes.POJO.Report;
import com.inn.notes.constants.NotesConstants;
import com.inn.notes.dao.ReportDao;
import com.inn.notes.service.ReportService;
import com.inn.notes.utils.NotesUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    ReportDao reportDao;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("Inside generateReport");
        try {
            String filename;
            if(validateRequestMap(requestMap)) {
                if(requestMap.containsKey("isGenerate") && !(boolean)requestMap.get("isGenerate")) {
                    filename = (String) requestMap.get("uuid");
                } else {
                    filename = NotesUtils.getUUID();
                    requestMap.put("uuid", filename);
                    insertReport(requestMap);
                }

                String data = "\nName: " + requestMap.get("name") + "\nEmail: " + requestMap.get("email");
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(NotesConstants.STORE_LOCATION+"\\"+filename+".pdf"));

                document.open();
                setRectangleInPdf(document);

                Paragraph chunk = new Paragraph("Your Notes", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data+"\n\n", getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = NotesUtils.getJsonArrayFromString((String) requestMap.get("noteDetails"));
                for(int i = 0; i < jsonArray.length(); i++) {
                    addRows(table, NotesUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("\nAll of your notes in one simple page :)" +
                        "\nThank you for using the app!", getFont("Data"));
                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"uuid\":\"" + filename + "\"}", HttpStatus.OK);
            }
            return NotesUtils.getResponseEntity("Required data not found.", HttpStatus.BAD_REQUEST);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("noteDetails");
    }

    private void insertReport(Map<String, Object> requestMap) {
        try {
            Report report = new Report();

            report.setUuid((String) requestMap.get("uuid"));
            report.setName((String) requestMap.get("name"));
            report.setEmail((String) requestMap.get("email"));
            report.setNoteDetails((String) requestMap.get("noteDetails"));
            report.setCreatedBy(jwtFilter.getCurrentUser());

            reportDao.save(report);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private Font getFont(String type) {
        log.info("Inside getFont");
        switch(type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 15, BaseColor.BLACK);
                dataFont.setStyle(Font.NORMAL);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");
        Stream.of("Title", "Category", "Description").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle));
            header.setBackgroundColor(BaseColor.GREEN);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell((String) data.get("title"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("description"));
    }

    @Override
    public ResponseEntity<List<Report>> getReports() {
        List<Report> list = new ArrayList<>();
        if(jwtFilter.isAdmin()) {
            list = reportDao.getAllReports();
        } else {
            list = reportDao.getReportByUsername(jwtFilter.getCurrentUser());
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside getPdf: request map {}", requestMap);
        try {
            byte[] byteArray = new byte[0];
            if(!requestMap.containsKey("uuid") && validateRequestMap(requestMap)) {
                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
            }
            String filePath = NotesConstants.STORE_LOCATION +"\\" + (String) requestMap.get("uuid") + ".pdf";
            if(NotesUtils.doesFileExist(filePath)) {
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            } else {
                requestMap.put("isGenerate", false);
                generateReport(requestMap);
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private byte[] getByteArray(String filePath) throws Exception{
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

    @Override
    public ResponseEntity<String> deleteReport(Integer id) {
        try {
            Optional optional = reportDao.findById(id);
            if(!optional.isEmpty()) {
                reportDao.deleteById(id);
                return NotesUtils.getResponseEntity("Report deleted successfully!", HttpStatus.OK);
            }
            return NotesUtils.getResponseEntity("Report id doesn't exist.", HttpStatus.OK);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
