package padthai.service.participant;

import padthai.dto.participant.request.LoginRequest;
import padthai.dto.participant.request.ProfileUpdateRequest;
import padthai.dto.participant.response.ProfileResponse;
import padthai.model.participant.User;
import padthai.repository.participant.UserRepository;
import padthai.service.event.EventService;
import padthai.service.event.ReportService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository users;
  private final EventService events;
  private final ReportService reports;

  public UserService(UserRepository users, EventService events, ReportService reports) {
    this.users = users;
    this.events = events;
    this.reports = reports;
  }

  public User login(LoginRequest request) {
    return users.login(request.email(), request.password());
  }

  public ProfileResponse profile(int id) {
    return new ProfileResponse(users.find(id), events.byOwner(id), reports.byAuthor(id));
  }

  public ProfileResponse update(int id, ProfileUpdateRequest request) {
    users.update(id, request.name(), request.department(), request.field(), request.bio());
    return profile(id);
  }
}
