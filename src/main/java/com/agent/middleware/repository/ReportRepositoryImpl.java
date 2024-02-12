package com.agent.middleware.repository;

import com.agent.middleware.entity.Report;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ReportRepositoryImpl implements ReportRepository{
    @Override
    public List<Report> findAll() {
        return null;
    }

    @Override
    public List<Report> findByDateBetween(Date startDate, Date endDate) {
        return null;
    }
}
