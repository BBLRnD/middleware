package com.agent.middleware.service;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;

public interface ReportService {
    void exportJasperReport(HttpServletResponse response, Date startDate, Date endDate);

    void exportExcelReport(HttpServletResponse response, Date startDate, Date endDate);
}
