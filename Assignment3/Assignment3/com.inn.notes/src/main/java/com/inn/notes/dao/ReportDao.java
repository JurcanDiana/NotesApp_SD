package com.inn.notes.dao;

import com.inn.notes.POJO.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportDao extends JpaRepository<Report, Integer> {

    List<Report> getAllReports();

    List<Report> getReportByUsername(@Param("username") String username);
}
