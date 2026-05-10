package com.example.casualconnect.service;

import com.example.casualconnect.dto.EventRequest;
import com.example.casualconnect.models.Event;
import com.example.casualconnect.repository.EventRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
  private final EventRepository events;
  public EventService(EventRepository events) { this.events = events; }

  public List<Event> findAll(String category, String tag, int userId) { return events.findAll(category, tag, userId); }
  public Event find(int id, int userId) { return events.find(id, userId); }
  public Event create(EventRequest request) { return events.create(request); }
  public Event update(int id, EventRequest request) { return events.update(id, request); }
  public void delete(int id, int ownerId) { events.delete(id, ownerId); }
  public void join(int eventId, int userId) { events.join(eventId, userId); }
  public void cancel(int eventId, int userId) { events.cancel(eventId, userId); }
  public List<Event> byOwner(int ownerId) { return events.byOwner(ownerId); }
}
