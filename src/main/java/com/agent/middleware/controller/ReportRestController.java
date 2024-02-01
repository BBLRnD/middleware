package com.agent.middleware.controller;

import com.agent.middleware.entity.Report;
import com.agent.middleware.repository.ReportRepository;
import com.agent.middleware.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReportRestController {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportService reportService;

    @GetMapping("/getReports")
    public List<Report> getReports() {
        List<Report> reports = (List<Report>) reportRepository.findAll();
        return reports;
    }

    @GetMapping("/public/pdfReport")
    public void createPDF(
            HttpServletResponse response,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws IOException, JRException {
        reportService.exportJasperReport(response, startDate, endDate);
    }

    @GetMapping("/public/excelReport")
    public void createExcel(
            HttpServletResponse response,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) throws IOException {
        reportService.exportExcelReport(response, startDate, endDate);
    }
}
