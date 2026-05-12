package padthai.controller.event;

import padthai.dto.event.request.CommentCreateRequest;
import padthai.dto.event.request.ReportCreateRequest;
import padthai.dto.event.response.ReportResponse;
import padthai.service.event.ReportService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {
  private final ReportService reports;

  public ReportController(ReportService reports) {
    this.reports = reports;
  }

  @GetMapping
  List<ReportResponse> list() {
    return reports.findAll();
  }

  @PostMapping
  ReportResponse create(@RequestBody ReportCreateRequest request) {
    return reports.create(request);
  }

  @PostMapping("/{id}/like")
  void like(@PathVariable int id) {
    reports.like(id);
  }

  @PostMapping("/{id}/comments")
  void comment(@PathVariable int id, @RequestBody CommentCreateRequest request) {
    reports.comment(id, request.comment());
  }
}
