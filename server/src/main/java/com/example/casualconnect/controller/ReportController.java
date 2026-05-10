package com.example.casualconnect.controller;

import com.example.casualconnect.dto.CommentRequest;
import com.example.casualconnect.dto.ReportRequest;
import com.example.casualconnect.models.Report;
import com.example.casualconnect.service.ReportService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
  private final ReportService reports;
  public ReportController(ReportService reports) { this.reports = reports; }

  @GetMapping
  List<Report> list() { return reports.findAll(); }

  @PostMapping
  Report create(@RequestBody ReportRequest request) { return reports.create(request); }

  @PostMapping("/{id}/like")
  void like(@PathVariable int id) { reports.like(id); }

  @PostMapping("/{id}/comments")
  void comment(@PathVariable int id, @RequestBody CommentRequest request) { reports.comment(id, request.comment()); }
}
