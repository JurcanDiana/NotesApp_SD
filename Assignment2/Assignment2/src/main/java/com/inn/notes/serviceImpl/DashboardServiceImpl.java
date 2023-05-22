package com.inn.notes.serviceImpl;

import com.inn.notes.dao.CategoryDao;
import com.inn.notes.dao.NoteDao;
import com.inn.notes.dao.ReportDao;
import com.inn.notes.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    NoteDao noteDao;

    @Autowired
    ReportDao reportDao;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryDao.count());
        map.put("note", noteDao.count());
        map.put("report", reportDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
