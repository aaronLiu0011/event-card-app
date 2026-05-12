package padthai.service.event;

import padthai.dto.event.request.ReportCreateRequest;
import padthai.dto.event.response.ReportResponse;
import padthai.model.event.Report;
import padthai.repository.event.ReportRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReportService {
  private final ReportRepository reports;

  public ReportService(ReportRepository reports) {
    this.reports = reports;
  }

  public List<ReportResponse> findAll() {
    return reports.findAll().stream().map(this::toResponse).toList();
  }

  public ReportResponse create(ReportCreateRequest request) {
    return toResponse(reports.create(request));
  }

  public void like(int id) {
    reports.like(id);
  }

  public void comment(int id, String comment) {
    reports.comment(id, comment);
  }

  public List<ReportResponse> byAuthor(int authorId) {
    return reports.byAuthor(authorId).stream().map(this::toResponse).toList();
  }

  private ReportResponse toResponse(Report report) {
    return new ReportResponse(report.id(), report.eventId(), report.authorId(), report.eventTitle(), report.authorName(),
      report.title(), report.body(), report.visibility(), report.likes(), report.comments(), report.createdAt());
  }
}
