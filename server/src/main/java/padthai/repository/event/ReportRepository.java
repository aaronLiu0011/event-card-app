package padthai.repository.event;

import padthai.dto.event.request.ReportCreateRequest;
import padthai.model.event.Report;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ReportRepository {
  private final JdbcTemplate jdbc;

  public ReportRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Report> findAll() {
    return jdbc.query(baseSql() + " ORDER BY r.created_at DESC", reportMapper());
  }

  public Report create(ReportCreateRequest request) {
    Integer id = jdbc.queryForObject("INSERT INTO reports (event_id, author_id, title, body, visibility) VALUES (?,?,?,?,?) RETURNING id", Integer.class,
      request.eventId(), request.authorId(), request.title(), request.body(), request.visibility());
    return find(id);
  }

  public void like(int id) {
    jdbc.update("UPDATE reports SET likes=likes+1 WHERE id=?", id);
  }

  public void comment(int id, String comment) {
    jdbc.update("UPDATE reports SET comments=CONCAT(comments, ?) WHERE id=?", "\n" + comment, id);
  }

  public List<Report> byAuthor(int authorId) {
    return jdbc.query(baseSql() + " WHERE r.author_id=? ORDER BY r.created_at DESC", reportMapper(), authorId);
  }

  private Report find(int id) {
    return jdbc.queryForObject(baseSql() + " WHERE r.id=?", reportMapper(), id);
  }

  private String baseSql() {
    return "SELECT r.*, e.title event_title, u.name author_name FROM reports r JOIN events e ON r.event_id=e.id JOIN users u ON r.author_id=u.id";
  }

  private org.springframework.jdbc.core.RowMapper<Report> reportMapper() {
    return (rs, rowNum) -> new Report(rs.getInt("id"), rs.getInt("event_id"), rs.getInt("author_id"),
      rs.getString("event_title"), rs.getString("author_name"), rs.getString("title"), rs.getString("body"),
      rs.getString("visibility"), rs.getInt("likes"), rs.getString("comments"), rs.getTimestamp("created_at").toLocalDateTime());
  }
}
