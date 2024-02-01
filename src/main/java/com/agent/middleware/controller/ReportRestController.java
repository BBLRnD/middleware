package com.agent.middleware.controller;

import com.agent.middleware.entity.Report;
import com.agent.middleware.repository.ReportRepository;
import com.agent.middleware.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReportRestController {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportService reportService;

    @GetMapping("/get-reports")
    public List<Report> getReports() {
        List<Report> reports = (List<Report>) reportRepository.findAll();
        return reports;
    }

    @GetMapping("/public/pdf-report")
    public void createPDF(

            HttpServletResponse response,
            @RequestParam String startDateStr,
            @RequestParam String endDateStr) throws IOException, JRException, ParseException{

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);

        reportService.exportJasperReport(response, startDate, endDate);


    }



    @GetMapping("/public/excel-report")
    public void createExcel(
            HttpServletResponse response,
            @RequestParam String startDateStr,
            @RequestParam String endDateStr) throws IOException, ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = dateFormat.parse(startDateStr);
        Date endDate = dateFormat.parse(endDateStr);

        reportService.exportExcelReport(response, startDate, endDate);
    }


}
