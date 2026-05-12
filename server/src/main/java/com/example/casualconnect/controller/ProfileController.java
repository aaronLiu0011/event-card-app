package com.example.casualconnect.controller;

import com.example.casualconnect.dto.ProfileRequest;
import com.example.casualconnect.dto.ProfileResponse;
import com.example.casualconnect.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class ProfileController {
  private final UserService users;
  public ProfileController(UserService users) { this.users = users; }

  @GetMapping("/{id}")
  ProfileResponse profile(@PathVariable int id) { return users.profile(id); }

  @PutMapping("/{id}")
  ProfileResponse update(@PathVariable int id, @RequestBody ProfileRequest request) { return users.update(id, request); }
}
