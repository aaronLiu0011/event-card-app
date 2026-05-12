package padthai.model.event;

import java.time.LocalDateTime;

public record Report(int id, int eventId, int authorId, String eventTitle, String authorName,
                     String title, String body, String visibility, int likes, String comments,
                     LocalDateTime createdAt) {}
