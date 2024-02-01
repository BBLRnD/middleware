package com.agent.middleware.repository;

import com.agent.middleware.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report,Integer> {

    List<Report> findByDateBetween(Date startDate, Date endDate);

}
