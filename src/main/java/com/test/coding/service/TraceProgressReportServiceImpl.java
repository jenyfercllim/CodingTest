package com.test.coding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.coding.model.TraceProgressReport;
import com.test.coding.repository.TraceProgressReportRepository;

@Service
public class TraceProgressReportServiceImpl implements TraceProgressReportService {
	
	@Autowired
	private TraceProgressReportRepository traceProgressReportRepository;
	
	@Override
	public void saveTraceProgress(TraceProgressReport traceProgressReport) {
		traceProgressReportRepository.save(traceProgressReport);
	}
}
