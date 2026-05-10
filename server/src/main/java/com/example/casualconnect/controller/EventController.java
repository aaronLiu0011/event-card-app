package com.example.casualconnect.controller;

import com.example.casualconnect.dto.EventRequest;
import com.example.casualconnect.dto.JoinRequest;
import com.example.casualconnect.models.Event;
import com.example.casualconnect.service.EventService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {
  private final EventService events;
  public EventController(EventService events) { this.events = events; }

  @GetMapping
  List<Event> list(@RequestParam(defaultValue = "") String category, @RequestParam(defaultValue = "") String tag,
                   @RequestParam(defaultValue = "0") int userId) {
    return events.findAll(category, tag, userId);
  }

  @GetMapping("/{id}")
  Event detail(@PathVariable int id, @RequestParam(defaultValue = "0") int userId) { return events.find(id, userId); }

  @PostMapping
  Event create(@RequestBody EventRequest request) { return events.create(request); }

  @PutMapping("/{id}")
  Event update(@PathVariable int id, @RequestBody EventRequest request) { return events.update(id, request); }

  @DeleteMapping("/{id}")
  void delete(@PathVariable int id, @RequestParam int ownerId) { events.delete(id, ownerId); }

  @PostMapping("/{id}/join")
  void join(@PathVariable int id, @RequestBody JoinRequest request) { events.join(id, request.userId()); }

  @DeleteMapping("/{id}/join")
  void cancel(@PathVariable int id, @RequestParam int userId) { events.cancel(id, userId); }
}
