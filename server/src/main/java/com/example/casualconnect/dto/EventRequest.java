package com.example.casualconnect.dto;

public record EventRequest(String title, String category, String tags, String startAt, String location,
                           int capacity, String imageUrl, String description, int ownerId) {}
