package com.test.coding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.coding.model.TraceProgressReport;

@Repository
public interface TraceProgressReportRepository extends JpaRepository<TraceProgressReport, Long> {
}
