package padthai.dto.event.response;

import padthai.model.event.Status;
import java.time.LocalDateTime;

public record EventResponse(int id, String title, String category, String tags, LocalDateTime startAt,
                            String location, int capacity, String imageUrl, String description,
                            int ownerId, String ownerName, int participants, boolean joined, Status status) {}
