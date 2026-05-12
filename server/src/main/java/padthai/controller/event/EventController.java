package padthai.controller.event;

import padthai.dto.event.request.EventCreateRequest;
import padthai.dto.event.request.EventUpdateRequest;
import padthai.dto.event.response.EventResponse;
import padthai.dto.participant.request.ParticipantCreateRequest;
import padthai.service.event.EventService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {
  private final EventService events;

  public EventController(EventService events) {
    this.events = events;
  }

  @GetMapping
  List<EventResponse> list(@RequestParam(defaultValue = "") String category, @RequestParam(defaultValue = "") String tag,
                           @RequestParam(defaultValue = "0") int userId) {
    return events.findAll(category, tag, userId);
  }

  @GetMapping("/{id}")
  EventResponse detail(@PathVariable int id, @RequestParam(defaultValue = "0") int userId) {
    return events.find(id, userId);
  }

  @PostMapping
  EventResponse create(@RequestBody EventCreateRequest request) {
    return events.create(request);
  }

  @PutMapping("/{id}")
  EventResponse update(@PathVariable int id, @RequestBody EventUpdateRequest request) {
    return events.update(id, request);
  }

  @DeleteMapping("/{id}")
  void delete(@PathVariable int id, @RequestParam int ownerId) {
    events.delete(id, ownerId);
  }

  @PostMapping("/{id}/join")
  void join(@PathVariable int id, @RequestBody ParticipantCreateRequest request) {
    events.join(id, request.userId());
  }

  @DeleteMapping("/{id}/join")
  void cancel(@PathVariable int id, @RequestParam int userId) {
    events.cancel(id, userId);
  }
}
