package com.example.casualconnect.dto;

import com.example.casualconnect.models.Event;
import com.example.casualconnect.models.Report;
import com.example.casualconnect.models.User;
import java.util.List;

public record ProfileResponse(User user, List<Event> events, List<Report> reports) {}
