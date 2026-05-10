package com.example.casualconnect.service;

import com.example.casualconnect.dto.*;
import com.example.casualconnect.models.User;
import com.example.casualconnect.repository.UserRepository;
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

  public User login(LoginRequest request) { return users.login(request.email(), request.password()); }

  public ProfileResponse profile(int id) {
    return new ProfileResponse(users.find(id), events.byOwner(id), reports.byAuthor(id));
  }

  public ProfileResponse update(int id, ProfileRequest request) {
    users.update(id, request.name(), request.department(), request.field(), request.bio());
    return profile(id);
  }
}
