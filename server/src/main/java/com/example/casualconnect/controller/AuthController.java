package com.example.casualconnect.controller;

import com.example.casualconnect.dto.LoginRequest;
import com.example.casualconnect.models.User;
import com.example.casualconnect.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {
  private final UserService users;
  public AuthController(UserService users) { this.users = users; }

  @PostMapping("/login")
  User login(@RequestBody LoginRequest request) { return users.login(request); }
}
