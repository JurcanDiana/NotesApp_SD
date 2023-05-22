package com.inn.notes.restImpl;

import com.inn.notes.POJO.Report;
import com.inn.notes.constants.NotesConstants;
import com.inn.notes.rest.ReportRest;
import com.inn.notes.service.ReportService;
import com.inn.notes.utils.NotesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ReportRestImpl implements ReportRest {

    @Autowired
    ReportService reportService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try {
            return reportService.generateReport(requestMap);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Report>> getReports() {
        try {
            return reportService.getReports();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try {
            return reportService.getPdf(requestMap);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteReport(Integer id) {
        try {
            return reportService.deleteReport(id);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return NotesUtils.getResponseEntity(NotesConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
