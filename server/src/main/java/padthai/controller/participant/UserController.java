package padthai.controller.participant;

import padthai.dto.participant.request.LoginRequest;
import padthai.dto.participant.request.ProfileUpdateRequest;
import padthai.dto.participant.response.ProfileResponse;
import padthai.model.participant.User;
import padthai.service.participant.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {
  private final UserService users;

  public UserController(UserService users) {
    this.users = users;
  }

  @PostMapping("/login")
  User login(@RequestBody LoginRequest request) {
    return users.login(request);
  }

  @GetMapping("/users/{id}")
  ProfileResponse profile(@PathVariable int id) {
    return users.profile(id);
  }

  @PutMapping("/users/{id}")
  ProfileResponse update(@PathVariable int id, @RequestBody ProfileUpdateRequest request) {
    return users.update(id, request);
  }
}
