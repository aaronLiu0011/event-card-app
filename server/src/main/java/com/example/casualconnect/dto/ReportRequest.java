package com.example.casualconnect.dto;

public record ReportRequest(int eventId, int authorId, String title, String body, String visibility) {}
