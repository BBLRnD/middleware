package com.agent.middleware.repository;

import com.agent.middleware.entity.Report;

import java.util.Date;
import java.util.List;

public interface ReportRepository {

    List<Report> findAll();

    List<Report> findByDateBetween(Date startDate, Date endDate);
}
