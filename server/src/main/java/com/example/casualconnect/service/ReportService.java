package com.example.casualconnect.service;

import com.example.casualconnect.dto.ReportRequest;
import com.example.casualconnect.models.Report;
import com.example.casualconnect.repository.ReportRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {
  private final ReportRepository reports;
  public ReportService(ReportRepository reports) { this.reports = reports; }

  public List<Report> findAll() { return reports.findAll(); }
  public Report create(ReportRequest request) { return reports.create(request); }
  public void like(int id) { reports.like(id); }
  public void comment(int id, String comment) { reports.comment(id, comment); }
  public List<Report> byAuthor(int authorId) { return reports.byAuthor(authorId); }
}
