package padthai.service.event;

import padthai.dto.event.request.EventCreateRequest;
import padthai.dto.event.request.EventUpdateRequest;
import padthai.dto.event.response.EventResponse;
import padthai.model.event.Event;
import padthai.repository.event.EventRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
  private final EventRepository events;

  public EventService(EventRepository events) {
    this.events = events;
  }

  public List<EventResponse> findAll(String category, String tag, int userId) {
    return events.findAll(category, tag, userId).stream().map(this::toResponse).toList();
  }

  public EventResponse find(int id, int userId) {
    return toResponse(events.find(id, userId));
  }

  public EventResponse create(EventCreateRequest request) {
    return toResponse(events.create(request));
  }

  public EventResponse update(int id, EventUpdateRequest request) {
    return toResponse(events.update(id, request));
  }

  public void delete(int id, int ownerId) {
    events.delete(id, ownerId);
  }

  public void join(int eventId, int userId) {
    events.join(eventId, userId);
  }

  public void cancel(int eventId, int userId) {
    events.cancel(eventId, userId);
  }

  public List<EventResponse> byOwner(int ownerId) {
    return events.byOwner(ownerId).stream().map(this::toResponse).toList();
  }

  private EventResponse toResponse(Event event) {
    return new EventResponse(event.id(), event.title(), event.category(), event.tags(), event.startAt(), event.location(),
      event.capacity(), event.imageUrl(), event.description(), event.ownerId(), event.ownerName(), event.participants(), event.joined(), event.status());
  }
}
