package padthai.dto.event.response;

import java.time.LocalDateTime;

public record ReportResponse(int id, int eventId, int authorId, String eventTitle, String authorName,
                             String title, String body, String visibility, int likes, String comments,
                             LocalDateTime createdAt) {}
