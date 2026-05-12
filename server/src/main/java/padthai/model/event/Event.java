package padthai.model.event;

import java.time.LocalDateTime;

public record Event(int id, String title, String category, String tags, LocalDateTime startAt,
                    String location, int capacity, String imageUrl, String description,
                    int ownerId, String ownerName, int participants, boolean joined, Status status) {}
