package com.agent.middleware.service;

import com.agent.middleware.entity.Report;
import com.agent.middleware.repository.ReportRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public void exportJasperReport(HttpServletResponse response, Date startDate, Date endDate) throws JRException, IOException {


        List<Report> reports = reportRepository.findByDateBetween(startDate, endDate);


        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + startDate + "_" + endDate + ".pdf";
        response.setHeader(headerKey, headerValue);

        File file = ResourceUtils.getFile("classpath:Report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reports);
        Map<String, Object> parameters = new HashMap<>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }



    public void exportExcelReport(HttpServletResponse response, Date startDate, Date endDate) throws IOException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            List<Report> reports = reportRepository.findByDateBetween(startDate, endDate);



            response.setContentType("text/xls");

            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=xls_" + startDate + "_" + endDate + ".xls";
            response.setHeader(headerKey, headerValue);

            try (PrintWriter writer = response.getWriter()) {
                writer.println("ID,First Name,Last Name,Street,City, Date");

                for (Report excelReport : reports) {
                    writer.println(
                            excelReport.getId() + "," +
                                    excelReport.getFirstname() + "," +
                                    excelReport.getLastname() + "," +
                                    excelReport.getStreet() + "," +
                                    excelReport.getCity() + "," +
                                    excelReport.getDate()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error parsing date strings", e);
        }
    }
}
