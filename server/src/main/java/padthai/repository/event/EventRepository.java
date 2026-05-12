package padthai.repository.event;

import padthai.dto.event.request.EventCreateRequest;
import padthai.dto.event.request.EventUpdateRequest;
import padthai.model.event.Event;
import padthai.model.event.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class EventRepository {
  private final JdbcTemplate jdbc;

  public EventRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Event> findAll(String category, String tag, int userId) {
    String sql = "SELECT e.*, u.name owner_name, COUNT(p.user_id) participants, " +
      "EXISTS (SELECT 1 FROM participants px WHERE px.event_id=e.id AND px.user_id=?) joined " +
      "FROM events e JOIN users u ON e.owner_id=u.id LEFT JOIN participants p ON e.id=p.event_id " +
      "WHERE (?='' OR e.category=?) AND (?='' OR e.tags ILIKE ?) GROUP BY e.id, u.name ORDER BY e.start_at";
    return jdbc.query(sql, eventMapper(), userId, category, category, tag, "%" + tag + "%");
  }

  public Event find(int id, int userId) {
    String sql = "SELECT e.*, u.name owner_name, COUNT(p.user_id) participants, " +
      "EXISTS (SELECT 1 FROM participants px WHERE px.event_id=e.id AND px.user_id=?) joined " +
      "FROM events e JOIN users u ON e.owner_id=u.id LEFT JOIN participants p ON e.id=p.event_id " +
      "WHERE e.id=? GROUP BY e.id, u.name";
    return jdbc.queryForObject(sql, eventMapper(), userId, id);
  }

  public Event create(EventCreateRequest request) {
    Integer id = jdbc.queryForObject("INSERT INTO events (title, category, tags, start_at, location, capacity, image_url, description, owner_id) VALUES (?,?,?,CAST(? AS TIMESTAMP),?,?,?,?,?) RETURNING id", Integer.class,
      request.title(), request.category(), request.tags(), request.startAt(), request.location(), request.capacity(), request.imageUrl(), request.description(), request.ownerId());
    return find(id, request.ownerId());
  }

  public Event update(int id, EventUpdateRequest request) {
    jdbc.update("UPDATE events SET title=?, category=?, tags=?, start_at=CAST(? AS TIMESTAMP), location=?, capacity=?, image_url=?, description=? WHERE id=? AND owner_id=?",
      request.title(), request.category(), request.tags(), request.startAt(), request.location(), request.capacity(), request.imageUrl(), request.description(), id, request.ownerId());
    return find(id, request.ownerId());
  }

  public void delete(int id, int ownerId) {
    jdbc.update("DELETE FROM events WHERE id=? AND owner_id=?", id, ownerId);
  }

  public void join(int eventId, int userId) {
    jdbc.update("INSERT INTO participants (event_id, user_id) VALUES (?, ?) ON CONFLICT DO NOTHING", eventId, userId);
  }

  public void cancel(int eventId, int userId) {
    jdbc.update("DELETE FROM participants WHERE event_id=? AND user_id=?", eventId, userId);
  }

  public List<Event> byOwner(int ownerId) {
    return jdbc.query("SELECT e.*, u.name owner_name, 0 participants, false joined FROM events e JOIN users u ON e.owner_id=u.id WHERE owner_id=? ORDER BY start_at DESC", eventMapper(), ownerId);
  }

  private org.springframework.jdbc.core.RowMapper<Event> eventMapper() {
    return (rs, rowNum) -> new Event(rs.getInt("id"), rs.getString("title"), rs.getString("category"), rs.getString("tags"),
      rs.getTimestamp("start_at").toLocalDateTime(), rs.getString("location"), rs.getInt("capacity"),
      rs.getString("image_url"), rs.getString("description"), rs.getInt("owner_id"), rs.getString("owner_name"),
      rs.getInt("participants"), rs.getBoolean("joined"), Status.OPEN);
  }
}
