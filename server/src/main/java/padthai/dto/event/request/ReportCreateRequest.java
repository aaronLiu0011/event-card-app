package padthai.dto.event.request;

public record ReportCreateRequest(int eventId, int authorId, String title, String body, String visibility) {}
