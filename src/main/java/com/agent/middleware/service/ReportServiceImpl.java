package com.agent.middleware.service;

import com.agent.middleware.entity.Report;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService{

    private List<Report> findByDateBetween(Date startDate, Date endDate) {
        return new ArrayList<>();
    }


    @SneakyThrows
    public void exportJasperReport(HttpServletResponse response, Date startDate, Date endDate) {


        List<Report> reports = findByDateBetween(startDate, endDate);


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


    @SneakyThrows
    public void exportExcelReport(HttpServletResponse response, Date startDate, Date endDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            List<Report> reports = findByDateBetween(startDate, endDate);


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
